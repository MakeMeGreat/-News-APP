package com.example.aston_intensiv_final_project.sources.ui.onesource

import com.example.aston_intensiv_final_project.headlines.data.models.NewsResponse
import com.example.aston_intensiv_final_project.sources.data.SourcesRepository
import com.example.aston_intensiv_final_project.sources.data.models.SourcesResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class OneSourcePresenter(
    private val sourcesRepository: SourcesRepository,
) : MvpPresenter<OneSourceView>(){

    private var oneSourceNews: NewsResponse? = null


    fun getOneSourceNews(sourceId: String) {
        viewState.startLoading()
        sourcesRepository.getOneSourceNews(sourceId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<NewsResponse>() {
                override fun onNext(response: NewsResponse) {
                    oneSourceNews = response
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