package com.example.aston_intensiv_final_project.data.mapper

import com.example.aston_intensiv_final_project.data.model.news.ArticleDto
import com.example.aston_intensiv_final_project.data.model.news.NewsResponse
import com.example.aston_intensiv_final_project.data.model.news.SourceDto
import com.example.aston_intensiv_final_project.data.model.source.SourceInfoDto
import com.example.aston_intensiv_final_project.data.model.source.SourceResponse
import com.example.aston_intensiv_final_project.domain.model.news.ArticleDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.SourceDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceInfoDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceResponseDomainModel
import javax.inject.Inject

class DataToDomainMapper @Inject constructor() {
    fun mapNewsToDomainModel(newsResponse: NewsResponse): NewsResponseDomainModel {
        return NewsResponseDomainModel(
            status = newsResponse.status,
            totalResults = newsResponse.totalResults,
            articles = mapArticles(newsResponse.articles)
        )
    }

    private fun mapArticles(articles: MutableList<ArticleDto>): MutableList<ArticleDtoDomainModel> {
        val mutableList = mutableListOf<ArticleDtoDomainModel>()
        articles.forEach {
            mutableList.add(mapArticle(it))
        }
        return mutableList
    }

    private fun mapArticle(article: ArticleDto): ArticleDtoDomainModel {
        return ArticleDtoDomainModel(
            source = mapSource(article.source!!),
            author = article.author,
            title = article.title,
            description = article.description,
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            content = article.content
        )
    }

    private fun mapSource(source: SourceDto): SourceDtoDomainModel {
        return SourceDtoDomainModel(
            id = source.id,
            name = source.name
        )
    }

    fun mapSourcesToDomainModel(sourceResponse: SourceResponse): SourceResponseDomainModel {
        return SourceResponseDomainModel(
            status = sourceResponse.status,
            sources = mapSourceInfos(sourceResponse.sources)
        )
    }

    private fun mapSourceInfos(sourceInfos: List<SourceInfoDto>): MutableList<SourceInfoDtoDomainModel> {
        val mutableList = mutableListOf<SourceInfoDtoDomainModel>()
        sourceInfos.forEach {
            mutableList.add(mapSourceInfo(it))
        }
        return mutableList
    }

    private fun mapSourceInfo(sourceInfoDTO: SourceInfoDto): SourceInfoDtoDomainModel {
        return SourceInfoDtoDomainModel(
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