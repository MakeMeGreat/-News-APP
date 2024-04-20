package com.example.aston_intensiv_final_project.data.network

import javax.inject.Inject

class NetworkDataSource @Inject constructor()  {
    fun getGeneralNews(pageNumber: Int) =
        RetrofitObject.retrofitService.getGeneralNews(pageNumber = pageNumber)

    fun getBusinessNews(pageNumber: Int) =
        RetrofitObject.retrofitService.getBusinessNews(pageNumber = pageNumber)

    fun getScienceNews(pageNumber: Int) =
        RetrofitObject.retrofitService.getScienceNews(pageNumber = pageNumber)

    fun getSources() =
        RetrofitObject.retrofitService.getSources()

    fun getOneSourceNews(sourceId: String) =
        RetrofitObject.retrofitService.getOneSourceNews(sourceId = sourceId)

    fun getFilteredNews(
        from: String? ,
        language: String? ,
        sortBy: String?
    ) =
        RetrofitObject.retrofitService.getFilteredNews(
            from = from,
            language = language,
            sortBy = sortBy
        )

    fun getSearchNews(
        searchQuery: String
    ) =
        RetrofitObject.retrofitService.getSearchNews(q = searchQuery)
}