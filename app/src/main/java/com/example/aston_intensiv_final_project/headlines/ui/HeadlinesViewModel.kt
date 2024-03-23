package com.example.aston_intensiv_final_project.headlines.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aston_intensiv_final_project.headlines.data.models.NewsResponse
import com.example.aston_intensiv_final_project.headlines.data.repository.NewsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class HeadlinesViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val generalNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var generalNewsPage = 1

    init {
        getGeneralNews()
    }

    fun getGeneralNews() {
        generalNews.value = Resource.Loading()
        newsRepository.getGeneralNews(generalNewsPage)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<NewsResponse>() {
                override fun onNext(response: NewsResponse) {
                    generalNews.value = Resource.Success(response)
                }

                override fun onError(e: Throwable) {
                    generalNews.value = Resource.Error("smtng in vm rxjava happened wrong")
                }

                override fun onComplete() {

                }
            })
        }
//        viewModelScope.launch {
//        generalNews.value = Resource.Loading()
//        val response = newsRepository.getGeneralNews(generalNewsPage)
//        generalNews.value = handleGeneralNewsResponse(response)
//    }

//    private fun handleGeneralNewsResponse(response: NewsResponse) : Resource<NewsResponse> {
//        if (response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//                return Resource.Success(resultResponse)
//            }
//        }
//        return Resource.Error(response.message())
//    }
}