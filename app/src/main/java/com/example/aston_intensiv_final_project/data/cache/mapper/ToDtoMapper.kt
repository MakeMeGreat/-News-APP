package com.example.aston_intensiv_final_project.data.cache.mapper

import com.example.aston_intensiv_final_project.data.cache.model.CacheSourceDbo
import com.example.aston_intensiv_final_project.data.cache.model.CachedArticleDbo
import com.example.aston_intensiv_final_project.data.model.news.ArticleDto
import com.example.aston_intensiv_final_project.data.model.news.SourceDto
import com.example.aston_intensiv_final_project.data.model.source.SourceInfoDto
import javax.inject.Inject

class ToDtoMapper @Inject constructor() {
    fun mapToListOfArticleDto(articlesDbo: List<CachedArticleDbo>): MutableList<ArticleDto> {
        return articlesDbo.map { mapToArticleDto(it) }.toMutableList()
    }

    private fun mapToArticleDto(cachedArticleDbo: CachedArticleDbo) =
        ArticleDto(
            source = SourceDto(cachedArticleDbo.sourceId, cachedArticleDbo.sourceName),
            author = cachedArticleDbo.author,
            title = cachedArticleDbo.title,
            description = cachedArticleDbo.description,
            url = cachedArticleDbo.url,
            urlToImage = cachedArticleDbo.urlToImage,
            publishedAt = cachedArticleDbo.publishedAt,
            content = cachedArticleDbo.content,
        )


    fun mapToListOfSourceInfoDto(sourcesDbo: List<CacheSourceDbo>): List<SourceInfoDto> {
        return sourcesDbo.map { mapToSourceInfoDto(it) }
    }

    private fun mapToSourceInfoDto(sourceDbo: CacheSourceDbo) =
        SourceInfoDto(
            id = sourceDbo.id,
            name = sourceDbo.name,
            description = sourceDbo.description,
            url = sourceDbo.url,
            category = sourceDbo.category,
            language = sourceDbo.language,
            country = sourceDbo.country,
        )
}