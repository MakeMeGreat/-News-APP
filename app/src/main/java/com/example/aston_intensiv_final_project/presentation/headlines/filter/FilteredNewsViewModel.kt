package com.example.aston_intensiv_final_project.presentation.headlines.filter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aston_intensiv_final_project.data.RepositoryImpl
import com.example.aston_intensiv_final_project.data.mapper.DataToDomainMapper
import com.example.aston_intensiv_final_project.data.network.NetworkDataSource
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class FilteredNewsViewModel: ViewModel() {

    val filteredNews = MutableLiveData<NewsResponseModel>()

    //Todo: it should be created by DI
    private val repository = RepositoryImpl(NetworkDataSource(), DataToDomainMapper())
    private val mapper = DomainToPresentationMapper()

    fun getFilteredNews(date: String?, language: String, sortFilter: String?) {
        repository.getFilteredNews(
            from = date,
            language = language,
            sortBy = sortFilter
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<NewsResponseDomainModel>() {
                override fun onNext(response: NewsResponseDomainModel) {
                    val mappedResponse = mapper.mapNewsToPresentationModel(response)
                    filteredNews.value = mappedResponse
                }

                override fun onError(e: Throwable) {
                    Log.e("Filters", "problem in getting filtered news: $e")
                }

                override fun onComplete() {}
            })
    }
}