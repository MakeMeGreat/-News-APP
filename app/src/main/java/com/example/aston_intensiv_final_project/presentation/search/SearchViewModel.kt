package com.example.aston_intensiv_final_project.presentation.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.usecase.GetSearchNewsUseCase
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel(
    private val getSearchNewsUseCase: GetSearchNewsUseCase,
    private val mapper: DomainToPresentationMapper
) : ViewModel() {

    val searchLiveData = MutableLiveData<NewsResponseModel>()

    fun getSearchNews(q: String) {
        getSearchNewsUseCase(
            searchQuery = q
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<NewsResponseDomainModel>() {
                override fun onNext(response: NewsResponseDomainModel) {
                    val mappedResponse = mapper.mapNewsToPresentationModel(response)
                    searchLiveData.value = mappedResponse
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Log.e("search query", "$e")
                }

                override fun onComplete() {}
            })
    }

    class SearchViewModeLFactory @Inject constructor(
        private val getSearchNewsUseCase: GetSearchNewsUseCase,
        private val mapper: DomainToPresentationMapper
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(
                getSearchNewsUseCase = getSearchNewsUseCase,
                mapper = mapper
            ) as T
        }
    }
}