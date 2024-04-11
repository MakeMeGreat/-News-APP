package com.example.aston_intensiv_final_project.presentation.headlines.ui

import com.example.aston_intensiv_final_project.domain.Repository
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class HeadlinesBusinessPresenter(
    private val repository: Repository,
    private val mapper: DomainToPresentationMapper
) : MvpPresenter<HeadlinesView>() {

    private lateinit var newsResponse: NewsResponseModel

    var newsPage = 1

    init {
        getNews()
    }

    fun getNews() {
        viewState.startLoading()
        // was newsPage++ on next line and newsResponse variable was nullable
        repository.getBusinessNews(newsPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<NewsResponseDomainModel>() {
                override fun onNext(response: NewsResponseDomainModel) {
                    val mappedResponse = mapper.mapNewsToPresentationModel(response)
                    if (newsPage == 1) {
                        newsResponse = mappedResponse
                    } else {
                        newsResponse.articles.addAll(mappedResponse.articles)
                    }
                    viewState.endLoading()
                    viewState.showSuccess(newsResponse)
                    newsPage++
                }

                override fun onError(e: Throwable) {
                    viewState.endLoading()
                    viewState.showError("something went wrong in articles getting throw presenter and repository")
                }

                override fun onComplete() {}
            })
    }
}