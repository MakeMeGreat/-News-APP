package com.example.aston_intensiv_final_project.presentation.headlines.general

import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.usecase.GetCategorizedNewsUseCase
import com.example.aston_intensiv_final_project.presentation.headlines.HeadlinesView
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class HeadlinesGeneralPresenter(
    private val getCategorizedNewsUseCase: GetCategorizedNewsUseCase,
    private val mapper: DomainToPresentationMapper
) : MvpPresenter<HeadlinesView>() {

    private lateinit var newsResponse: NewsResponseModel

    private var pageNumber = 1

    init {
        getFirstNews()
    }

    fun getFirstNews() {
        viewState.startFirstLoading()
        getNews()
    }

    fun getNewsWithPagination() {
        viewState.startPaginateLoading()
        getNews()
    }

    private fun getNews() {
        getCategorizedNewsUseCase(category = "general", pageNumber = pageNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<NewsResponseDomainModel>() {
                override fun onNext(response: NewsResponseDomainModel) {
                    val mappedResponse = mapper.mapNewsToPresentationModel(response)
                    if (pageNumber == 1) {
                        newsResponse = mappedResponse
                    } else {
                        if (mappedResponse.status != "fromCache") {
                            newsResponse.articles.addAll(mappedResponse.articles)
                        }
                    }
                    viewState.endLoading()
                    if (mappedResponse.status == "fromCache" && mappedResponse.articles.isEmpty()) {
                        viewState.showNoInternet()
                        pageNumber--
                    }
                    viewState.showSuccess(newsResponse)
                    pageNumber++
                }

                override fun onError(e: Throwable) {
                    viewState.endLoading()
                    viewState.showError("something wrong in articles getting: $e")
                }

                override fun onComplete() {}
            })
    }

    fun refresh() {
        pageNumber = 1
        newsResponse.articles.clear()
    }

    fun getPageNumber() = pageNumber
}