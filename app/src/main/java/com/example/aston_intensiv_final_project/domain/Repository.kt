package com.example.aston_intensiv_final_project.domain

import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceResponseDomainModel
import io.reactivex.rxjava3.core.Observable

interface Repository {

    fun getGeneralNews(pageNumber: Int): Observable<NewsResponseDomainModel>

    fun getBusinessNews(pageNumber: Int): Observable<NewsResponseDomainModel>

    fun getScienceNews(pageNumber: Int): Observable<NewsResponseDomainModel>

    fun getSources(): Observable<SourceResponseDomainModel>

    fun getOneSourceNews(sourceId: String): Observable<NewsResponseDomainModel>

    fun getFilteredNews(
        from: String?,
        language: String?,
        sortBy: String?,
    ): Observable<NewsResponseDomainModel>
}

