package com.example.aston_intensiv_final_project.data.network

class NetworkDataSource {
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
}