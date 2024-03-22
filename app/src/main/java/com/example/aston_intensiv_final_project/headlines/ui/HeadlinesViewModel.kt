package com.example.aston_intensiv_final_project.headlines.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aston_intensiv_final_project.headlines.data.models.NewsResponse
import com.example.aston_intensiv_final_project.headlines.data.repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HeadlinesViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val generalNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var generalNewsPage = 1

    init {
        getGeneralNews()
    }

    fun getGeneralNews() = viewModelScope.launch {
        generalNews.value = Resource.Loading()
        val response = newsRepository.getGeneralNews(generalNewsPage)
        generalNews.value = handleGeneralNewsResponse(response)
    }

    private fun handleGeneralNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}