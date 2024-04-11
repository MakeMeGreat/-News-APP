package com.example.aston_intensiv_final_project.presentation.sources.data

import com.example.aston_intensiv_final_project.presentation.headlines.data.api.Retrofit

class SourcesRepository {

    fun getSources() =
        Retrofit.retrofitService.getSources()

    fun getOneSourceNews(sourceId: String) =
        Retrofit.retrofitService.getOneSourceNews(sourceId = sourceId)
}