package com.example.aston_intensiv_final_project.data.cache.mapper

import com.example.aston_intensiv_final_project.data.cache.CachedArticleDbo
import com.example.aston_intensiv_final_project.data.model.news.ArticleDto
import com.example.aston_intensiv_final_project.data.model.news.SourceDto
import javax.inject.Inject

class ToArticleDtoMapper @Inject constructor() {
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
}