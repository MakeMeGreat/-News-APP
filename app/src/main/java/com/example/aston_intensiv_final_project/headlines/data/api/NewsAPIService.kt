package com.example.aston_intensiv_final_project.headlines.data.api

import com.example.aston_intensiv_final_project.headlines.data.models.NewsResponse
import com.example.aston_intensiv_final_project.sources.data.models.SourcesResponse
import com.example.aston_intensiv_final_project.util.Constants.Companion.API_KEY
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET("v2/top-headlines")
    fun getGeneralNews(
        @Query("country")
        countryCode: String = "us",
        @Query("category")
        category: String = "general",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Observable<NewsResponse>

    @GET("v2/top-headlines")
    fun getBusinessNews(
        @Query("country")
        countryCode: String = "us",
        @Query("category")
        category: String = "business",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Observable<NewsResponse>

    @GET("v2/top-headlines")
    fun getScienceNews(
        @Query("country")
        countryCode: String = "us",
        @Query("category")
        category: String = "science",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Observable<NewsResponse>

    @GET("v2/top-headlines/sources")
    fun getSources(
//        @Query("country")
//        country: String = "us",
        @Query("apiKey")
        apiKey: String = API_KEY,
    ): Observable<SourcesResponse>
}