package com.example.aston_intensiv_final_project.data.mapper

import com.example.aston_intensiv_final_project.data.model.news.ArticleDto
import com.example.aston_intensiv_final_project.data.model.news.SourceDto
import com.example.aston_intensiv_final_project.domain.model.news.ArticleDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.SourceDtoDomainModel
import javax.inject.Inject

class DomainToDataMapper @Inject constructor() {

    fun mapArticle(article: ArticleDtoDomainModel): ArticleDto {
        return ArticleDto(
            mapSource(article.source!!),
            author = article.author,
            title = article.title,
            description = article.description,
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            content = article.content,
        )
    }

    private fun mapSource(source: SourceDtoDomainModel): SourceDto {
        return SourceDto(
            id = source.id,
            name = source.name,
        )
    }
}