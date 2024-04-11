package com.example.aston_intensiv_final_project.presentation.sources.ui.onesource

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
class OneSourcePresenter(
    private val repository: Repository,
    private val mapper: DomainToPresentationMapper
) : MvpPresenter<OneSourceView>() {

    private var oneSourceNews: NewsResponseModel? = null


    fun getOneSourceNews(sourceId: String) {
        viewState.startLoading()
        repository.getOneSourceNews(sourceId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<NewsResponseDomainModel>() {
                override fun onNext(response: NewsResponseDomainModel) {
                    oneSourceNews = mapper.mapNewsToPresentationModel(response)
                    viewState.endLoading()
                    viewState.showSuccess(oneSourceNews!!)
                }

                override fun onError(e: Throwable) {
                    viewState.endLoading()
                    viewState.showError("problem in getSources: $e")
                }

                override fun onComplete() {}
            })
    }
}