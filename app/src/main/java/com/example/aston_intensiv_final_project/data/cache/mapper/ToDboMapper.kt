package com.example.aston_intensiv_final_project.data.cache.mapper

import com.example.aston_intensiv_final_project.data.cache.CacheSourceDbo
import com.example.aston_intensiv_final_project.data.cache.CachedArticleDbo
import com.example.aston_intensiv_final_project.data.model.news.ArticleDto
import com.example.aston_intensiv_final_project.data.model.source.SourceInfoDto
import javax.inject.Inject

class ToDboMapper @Inject constructor() {
    fun mapArticle(
        article: ArticleDto,
        category: String? = null,

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

            )
    }

    fun mapSource(source: SourceInfoDto): CacheSourceDbo {
        return CacheSourceDbo(
            id = source.id ?: "",
            name = source.name,
            description = source.description,
            url = source.url,
            category = source.category,
            language = source.language,
            country = source.country,
        )
    }
}