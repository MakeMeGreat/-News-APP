package com.example.aston_intensiv_final_project.domain

import com.example.aston_intensiv_final_project.domain.model.news.ArticleDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceResponseDomainModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getCategorizedNews(category: String, pageNumber: Int): Observable<NewsResponseDomainModel>

    fun getSources(language: String, category: String): Observable<SourceResponseDomainModel>

    fun getOneSourceNews(sourceId: String): Observable<NewsResponseDomainModel>

    fun getFilteredNews(
        from: String?,
        language: String?,
        sortBy: String?,
    ): Observable<NewsResponseDomainModel>

    fun getSearchNews(searchQuery: String): Observable<NewsResponseDomainModel>

    suspend fun saveOrDeleteArticle(articleDtoDomainModel: ArticleDtoDomainModel)

    fun getSavedArticles(): Flow<List<ArticleDtoDomainModel>>
}

