package com.example.aston_intensiv_final_project.sources.ui

import com.example.aston_intensiv_final_project.sources.data.SourcesRepository
import com.example.aston_intensiv_final_project.sources.data.models.SourcesResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class SourcesPresenter(
    private val sourcesRepository: SourcesRepository
) : MvpPresenter<SourcesView>() {

    lateinit var sources: SourcesResponse

    init {
        getSources()
    }

    private fun getSources() {
        viewState.startLoading()
        sourcesRepository.getSources()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<SourcesResponse>() {
                override fun onNext(response: SourcesResponse) {
                    sources = response
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