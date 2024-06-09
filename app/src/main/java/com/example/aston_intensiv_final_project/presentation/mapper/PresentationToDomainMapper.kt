package com.example.aston_intensiv_final_project.presentation.mapper

import com.example.aston_intensiv_final_project.domain.model.news.ArticleDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.SourceDtoDomainModel
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleModel
import com.example.aston_intensiv_final_project.presentation.model.news.SourceModel
import javax.inject.Inject

class PresentationToDomainMapper @Inject constructor() {

    fun mapArticle(article: ArticleModel): ArticleDtoDomainModel {
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

    private fun mapSource(source: SourceModel): SourceDtoDomainModel {
        return SourceDtoDomainModel(
            id = source.id,
            name = source.name,
        )
    }
}