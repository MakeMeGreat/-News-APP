package com.example.aston_intensiv_final_project.sources.data

import com.example.aston_intensiv_final_project.headlines.data.api.Retrofit

class SourcesRepository {

    fun getSources() =
        Retrofit.retrofitService.getSources()
}