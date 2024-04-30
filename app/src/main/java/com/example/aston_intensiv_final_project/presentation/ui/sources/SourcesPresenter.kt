package com.example.aston_intensiv_final_project.presentation.ui.sources

import com.example.aston_intensiv_final_project.domain.usecase.GetSourcesUseCase
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.source.SourceResponseModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class SourcesPresenter(
    private val getSourcesUseCase: GetSourcesUseCase,
    private val mapper: DomainToPresentationMapper,
) : MvpPresenter<SourcesView>() {

    lateinit var sources: SourceResponseModel

    val disposables = CompositeDisposable()

    init {
        getSources()
    }

    fun refreshSource(language: String = "", category: String = "") {
        provideSourcesRightWay(language, category)
    }

    fun getSources(language: String = "", category: String = "") {
        viewState.startLoading()
        provideSourcesRightWay(language, category)
    }

    private fun provideSourcesRightWay(language: String, category: String) {
        disposables.add(
            getSourcesUseCase(language, category)
                .observeOn(AndroidSchedulers.mainThread())
                .map { mapper.mapSourcesToPresentationModel(it) }
                .subscribe(
                    { response -> handleResponse(response, language, category) },
                    { error -> handleError(error) }
                )
        )
    }

    private fun handleResponse(response: SourceResponseModel, language: String, category: String) {
        viewState.endLoading()
        sources = response
        if (language == "" && category == "" && sources.status == "fromCache" && sources.sources.isEmpty()) {
            viewState.showNoInternet()
        } else {
            viewState.showSuccess(sources)
        }
    }

    private fun handleError(e: Throwable) {
        viewState.endLoading()
        viewState.showError("problem in getSources: $e")
    }

}