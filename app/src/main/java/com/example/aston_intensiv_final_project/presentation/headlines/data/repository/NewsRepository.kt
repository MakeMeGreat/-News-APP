package com.example.aston_intensiv_final_project.presentation.headlines.data.repository

import com.example.aston_intensiv_final_project.presentation.headlines.data.api.Retrofit

class NewsRepository {
    fun getGeneralNews(pageNumber: Int) =
        Retrofit.retrofitService.getGeneralNews(pageNumber = pageNumber)

    fun getBusinessNews(pageNumber: Int) =
        Retrofit.retrofitService.getBusinessNews(pageNumber = pageNumber)

    fun getScienceNews(pageNumber: Int) =
        Retrofit.retrofitService.getScienceNews(pageNumber = pageNumber)
}