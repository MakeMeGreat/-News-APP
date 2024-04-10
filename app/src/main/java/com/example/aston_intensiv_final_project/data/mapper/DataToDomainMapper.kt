package com.example.aston_intensiv_final_project.data.mapper

import com.example.aston_intensiv_final_project.data.model.news.ArticleDTO
import com.example.aston_intensiv_final_project.data.model.news.NewsResponse
import com.example.aston_intensiv_final_project.data.model.news.SourceDTO
import com.example.aston_intensiv_final_project.data.model.source.SourceInfoDTO
import com.example.aston_intensiv_final_project.data.model.source.SourceResponse
import com.example.aston_intensiv_final_project.domain.model.news.ArticleDTODomainModel
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.SourceDTODomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceInfoDTODomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceResponseDomainModel

class DataToDomainMapper {
    fun mapNewsToDomainModel(newsResponse: NewsResponse): NewsResponseDomainModel {
        return NewsResponseDomainModel(
            status = newsResponse.status,
            totalResults = newsResponse.totalResults,
            articles = mapArticles(newsResponse.articles)
        )
    }

    private fun mapArticles(articles: MutableList<ArticleDTO>): MutableList<ArticleDTODomainModel> {
        val mutableList = mutableListOf<ArticleDTODomainModel>()
        articles.forEach {
            mutableList.add(mapArticle(it))
        }
        return mutableList
    }

    private fun mapArticle(article: ArticleDTO): ArticleDTODomainModel {
        return ArticleDTODomainModel(
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

    private fun mapSource(source: SourceDTO): SourceDTODomainModel {
        return SourceDTODomainModel(
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

    private fun mapSourceInfos(sourceInfos : List<SourceInfoDTO>): MutableList<SourceInfoDTODomainModel> {
        val mutableList = mutableListOf<SourceInfoDTODomainModel>()
        sourceInfos.forEach {
            mutableList.add(mapSourceInfo(it))
        }
        return mutableList
    }

    private fun mapSourceInfo(sourceInfoDTO: SourceInfoDTO): SourceInfoDTODomainModel{
        return SourceInfoDTODomainModel(
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