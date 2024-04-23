package com.example.aston_intensiv_final_project.presentation.sources.onesource

import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.usecase.GetOneSourceNewsUseCase
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class OneSourcePresenter(
    private val getOneSourceNewsUseCase: GetOneSourceNewsUseCase,
    private val mapper: DomainToPresentationMapper
) : MvpPresenter<OneSourceView>() {

//    private var oneSourceNews: NewsResponseModel? = null

    lateinit var oneSourceNews: NewsResponseModel

    fun getOneSourceNews(sourceId: String) {
        viewState.startLoading()
        getOneSourceNewsUseCase(sourceId = sourceId)
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