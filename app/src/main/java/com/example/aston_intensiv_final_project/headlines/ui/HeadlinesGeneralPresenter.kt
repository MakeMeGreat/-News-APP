package com.example.aston_intensiv_final_project.headlines.ui

import com.example.aston_intensiv_final_project.headlines.data.models.NewsResponse
import com.example.aston_intensiv_final_project.headlines.data.repository.NewsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class HeadlinesGeneralPresenter(
    private val newsRepository: NewsRepository
) : MvpPresenter<HeadlinesView>() {

    private var newsResponse: NewsResponse? = null
//    private var generalNewsResponse: NewsResponse? =  null
    var newsPage = 1

    init {
        getNews()
    }

    fun getNews() {
        viewState.startLoading()
        newsRepository.getGeneralNews(newsPage++)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<NewsResponse>() {
                override fun onNext(response: NewsResponse) {
                    if (newsResponse == null) {
                        newsResponse = response
                    } else {
                        newsResponse?.articles?.addAll(response.articles)
                    }
                    viewState.endLoading()
                    viewState.showSuccess(newsResponse!!)
                }

                override fun onError(e: Throwable) {
                    viewState.endLoading()
                    viewState.showError("something went wrong in articles getting throw presenter and repository")
                }

                override fun onComplete() {}
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