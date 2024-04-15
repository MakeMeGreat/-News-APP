package com.example.aston_intensiv_final_project.data.network


import com.example.aston_intensiv_final_project.data.model.news.NewsResponse
import com.example.aston_intensiv_final_project.data.model.source.SourceResponse
import com.example.aston_intensiv_final_project.util.Constants
import com.example.aston_intensiv_final_project.util.Constants.Companion.API_KEY
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

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
        @Query("apiKey")
        apiKey: String = API_KEY,
    ): Observable<SourceResponse>

    @GET("v2/top-headlines")
    fun getOneSourceNews(
        @Query("sources")
        sourceId: String,
        @Query("apiKey")
        apiKey: String = API_KEY,
    ): Observable<NewsResponse>

    @GET("v2/everything")
    fun getFilteredNews(
        @Query("q")
        query: String? = "null",
        @Query("from")
        from: String? = "2024-04-10",
        @Query("language")
        language: String? = "en",
        @Query("sortBy")
        sortBy: String? = "popularity",
        @Query("apiKey")
        apiKey: String = API_KEY,
        ): Observable<NewsResponse>
}