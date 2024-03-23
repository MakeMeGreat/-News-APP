package com.example.aston_intensiv_final_project.headlines.data.repository

import com.example.aston_intensiv_final_project.headlines.data.api.Retrofit

class NewsRepository(

) {
    fun getGeneralNews(pageNumber: Int) =
        Retrofit.retrofitService.getGeneralNews(pageNumber = pageNumber)
}