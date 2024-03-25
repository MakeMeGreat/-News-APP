package com.example.aston_intensiv_final_project.headlines.ui

import com.example.aston_intensiv_final_project.headlines.data.models.NewsResponse
import com.example.aston_intensiv_final_project.headlines.data.repository.NewsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class HeadlinesPresenter(
    private val newsRepository: NewsRepository
) : MvpPresenter<HeadlinesView>() {

    private var generalNews: NewsResponse? = null
//    private var generalNewsResponse: NewsResponse? =  null
    var generalNewsPage = 1

    init {
        getGeneralNews()
    }

    fun getGeneralNews() {
        viewState.startLoading()
        newsRepository.getGeneralNews(generalNewsPage++)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<NewsResponse>() {
                override fun onNext(response: NewsResponse) {

                    if (generalNews == null) {
                        generalNews = response
                    } else {
                        generalNews?.articles?.addAll(response.articles)
                    }
                    /*if (generalNewsResponse == null) {
                        generalNewsResponse = response
                        generalNews = response
                    } else {
                        generalNewsResponse = response
                        generalNewsResponse?.articles?.let { generalNews.articles.addAll(it) }
                    }*/
                    viewState.endLoading()
                    viewState.showSuccess(generalNews!!)
                }

                override fun onError(e: Throwable) {
                    viewState.endLoading()
                    viewState.showError("something went wrong in articles getting throw presenter and repository")
                }

                override fun onComplete() {

                }
            })
    }
    /*        viewModelScope.launch {
            generalNews.value = Resource.Loading()
            val response = newsRepository.getGeneralNews(generalNewsPage)
            generalNews.value = handleGeneralNewsResponse(response)
        }

        private fun handleGeneralNewsResponse(response: NewsResponse) : Resource<NewsResponse> {
            if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    return Resource.Success(resultResponse)
                }
            }
            return Resource.Error(response.message())
        }*/
}