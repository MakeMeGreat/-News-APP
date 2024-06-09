package com.example.aston_intensiv_final_project.presentation.mapper

import com.example.aston_intensiv_final_project.domain.model.news.ArticleDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.SourceDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceInfoDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceResponseDomainModel
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleModel
import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import com.example.aston_intensiv_final_project.presentation.model.news.SourceModel
import com.example.aston_intensiv_final_project.presentation.model.source.SourceInfoModel
import com.example.aston_intensiv_final_project.presentation.model.source.SourceResponseModel
import javax.inject.Inject

class DomainToPresentationMapper @Inject constructor() {

    fun mapNewsToPresentationModel(newsResponse: NewsResponseDomainModel): NewsResponseModel {
        return NewsResponseModel(
            status = newsResponse.status,
            totalResults = newsResponse.totalResults,
            articles = mapArticles(newsResponse.articles)
        )
    }

    fun mapArticles(articles: MutableList<ArticleDtoDomainModel>): MutableList<ArticleModel> {
        val mutableList = mutableListOf<ArticleModel>()
        articles.forEach {
            mutableList.add(mapArticle(it))
        }
        return mutableList
    }

    private fun mapArticle(article: ArticleDtoDomainModel): ArticleModel {
        return ArticleModel(
            source = mapSource(article.source),
            author = article.author,
            title = article.title,
            description = article.description,
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            content = article.content
        )
    }

    private fun mapSource(source: SourceDtoDomainModel?): SourceModel {
        return SourceModel(
            id = source?.id,
            name = source?.name
        )
    }

    fun mapSourcesToPresentationModel(sourceResponse: SourceResponseDomainModel): SourceResponseModel {
        return SourceResponseModel(
            status = sourceResponse.status,
            sources = mapSourceInfos(sourceResponse.sources)
        )
    }

    private fun mapSourceInfos(sourceInfos: List<SourceInfoDtoDomainModel>): MutableList<SourceInfoModel> {
        val mutableList = mutableListOf<SourceInfoModel>()
        sourceInfos.forEach {
            mutableList.add(mapSourceInfo(it))
        }
        return mutableList
    }

    private fun mapSourceInfo(sourceInfoDTO: SourceInfoDtoDomainModel): SourceInfoModel {
        return SourceInfoModel(
            id = sourceInfoDTO.id,
            name = sourceInfoDTO.name,
            description = sourceInfoDTO.description,
            url = sourceInfoDTO.url,
            category = sourceInfoDTO.category,
            language = sourceInfoDTO.language,
            country = sourceInfoDTO.country
        )
    }
}