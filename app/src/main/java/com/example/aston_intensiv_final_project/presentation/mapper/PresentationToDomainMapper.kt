package com.example.aston_intensiv_final_project.presentation.mapper

import com.example.aston_intensiv_final_project.domain.model.news.ArticleDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.SourceDtoDomainModel
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleDtoModel
import com.example.aston_intensiv_final_project.presentation.model.news.SourceDtoModel
import javax.inject.Inject

class PresentationToDomainMapper @Inject constructor() {

    fun mapArticle(article: ArticleDtoModel): ArticleDtoDomainModel {
        return ArticleDtoDomainModel(
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

    private fun mapSource(source: SourceDtoModel): SourceDtoDomainModel {
        return SourceDtoDomainModel(
            id = source.id,
            name = source.name,
        )
    }
}