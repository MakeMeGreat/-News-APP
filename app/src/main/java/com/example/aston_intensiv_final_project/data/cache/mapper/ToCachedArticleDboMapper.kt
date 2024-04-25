package com.example.aston_intensiv_final_project.data.cache.mapper

import com.example.aston_intensiv_final_project.data.cache.CachedArticleDbo
import com.example.aston_intensiv_final_project.data.model.news.ArticleDto
import javax.inject.Inject

class ToCachedArticleDboMapper @Inject constructor() {
    fun mapToCache(
        article: ArticleDto,
        category: String? = null,
        pageNumber: Int? = null
    ): CachedArticleDbo {
        return CachedArticleDbo(
            sourceId = article.source?.id,
            sourceName = article.source?.name,
            author = article.author,
            title = article.title ?: "",
            description = article.description,
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            content = article.content,
            category = category,
            pageNumber = pageNumber,
        )
    }
}