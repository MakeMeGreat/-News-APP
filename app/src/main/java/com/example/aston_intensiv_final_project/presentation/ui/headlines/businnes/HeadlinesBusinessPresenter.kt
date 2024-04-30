package com.example.aston_intensiv_final_project.presentation.ui.headlines.businnes

import com.example.aston_intensiv_final_project.domain.usecase.GetCategorizedNewsUseCase
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import com.example.aston_intensiv_final_project.presentation.ui.headlines.HeadlinesView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject


class HeadlinesBusinessPresenter @Inject constructor(
    private val getCategorizedNewsUseCase: GetCategorizedNewsUseCase,
    private val mapper: DomainToPresentationMapper,
) : MvpPresenter<HeadlinesView>() {

    private lateinit var newsResponse: NewsResponseModel

    private var pageNumber = 1

    private val disposables = CompositeDisposable()

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
        disposables.add(getCategorizedNewsUseCase(category = "business", pageNumber = pageNumber)
            .observeOn(AndroidSchedulers.mainThread())
            .map { mapper.mapNewsToPresentationModel(it) }
            .subscribe(
                { response -> handleResponse(response) },
                { error -> handleError(error) }
            ))
    }

    private fun handleResponse(response: NewsResponseModel) {
        viewState.endLoading()

        if (response.status == "fromCache" && response.articles.isEmpty()) {
            newsResponse = response
            viewState.showNoInternet()
            pageNumber--
        } else {
            if (pageNumber == 1) {
                newsResponse = response
            } else {
                newsResponse.articles += response.articles
            }
        }
        viewState.showSuccess(newsResponse)
        pageNumber++
    }

    private fun handleError(e: Throwable) {
        viewState.endLoading()
        viewState.showError("something wrong in articles getting: $e")
    }


    fun refresh() {
        pageNumber = 1
        newsResponse.articles.clear()
    }

    fun getPageNumber() = pageNumber

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}