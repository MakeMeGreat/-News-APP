package com.example.aston_intensiv_final_project.presentation.sources

import com.example.aston_intensiv_final_project.domain.Repository
import com.example.aston_intensiv_final_project.domain.model.source.SourceResponseDomainModel
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.source.SourceResponseModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class SourcesPresenter(
    private val repository: Repository,
    private val mapper: DomainToPresentationMapper,
) : MvpPresenter<SourcesView>() {

    lateinit var sources: SourceResponseModel

    init {
        getSources()
    }

    private fun getSources() {
        viewState.startLoading()
        repository.getSources()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<SourceResponseDomainModel>() {
                override fun onNext(response: SourceResponseDomainModel) {
                    sources = mapper.mapSourcesToPresentationModel(response)
                    viewState.endLoading()
                    viewState.showSuccess(sources)
                }

                override fun onError(e: Throwable) {
                    viewState.endLoading()
                    viewState.showError("problem in getSources: $e")
                }

                override fun onComplete() {}
            })
    }
}