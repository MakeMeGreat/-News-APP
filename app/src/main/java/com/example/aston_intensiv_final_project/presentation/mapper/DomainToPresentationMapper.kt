package com.example.aston_intensiv_final_project.presentation.mapper

import com.example.aston_intensiv_final_project.domain.model.news.ArticleDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.SourceDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceInfoDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceResponseDomainModel
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleDtoModel
import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import com.example.aston_intensiv_final_project.presentation.model.news.SourceDtoModel
import com.example.aston_intensiv_final_project.presentation.model.source.SourceInfoDtoModel
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

    fun mapArticles(articles: MutableList<ArticleDtoDomainModel>): MutableList<ArticleDtoModel> {
        val mutableList = mutableListOf<ArticleDtoModel>()
        articles.forEach {
            mutableList.add(mapArticle(it))
        }
        return mutableList
    }

    private fun mapArticle(article: ArticleDtoDomainModel): ArticleDtoModel {
        return ArticleDtoModel(
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

    private fun mapSource(source: SourceDtoDomainModel?): SourceDtoModel {
        return SourceDtoModel(
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

    private fun mapSourceInfos(sourceInfos: List<SourceInfoDtoDomainModel>): MutableList<SourceInfoDtoModel> {
        val mutableList = mutableListOf<SourceInfoDtoModel>()
        sourceInfos.forEach {
            mutableList.add(mapSourceInfo(it))
        }
        return mutableList
    }

    private fun mapSourceInfo(sourceInfoDTO: SourceInfoDtoDomainModel): SourceInfoDtoModel {
        return SourceInfoDtoModel(
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