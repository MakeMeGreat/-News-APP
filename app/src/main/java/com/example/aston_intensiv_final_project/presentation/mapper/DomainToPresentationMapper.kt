package com.example.aston_intensiv_final_project.presentation.mapper

import com.example.aston_intensiv_final_project.domain.model.news.ArticleDTODomainModel
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.SourceDTODomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceInfoDTODomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceResponseDomainModel
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleDTOModel
import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import com.example.aston_intensiv_final_project.presentation.model.news.SourceDTOModel
import com.example.aston_intensiv_final_project.presentation.model.source.SourceInfoDTOModel
import com.example.aston_intensiv_final_project.presentation.model.source.SourceResponseModel

class DomainToPresentationMapper {

    fun mapNewsToPresentationModel(newsResponse: NewsResponseDomainModel): NewsResponseModel {
        return NewsResponseModel(
            status = newsResponse.status,
            totalResults = newsResponse.totalResults,
            articles = mapArticles(newsResponse.articles)
        )
    }

    private fun mapArticles(articles: MutableList<ArticleDTODomainModel>): MutableList<ArticleDTOModel> {
        val mutableList = mutableListOf<ArticleDTOModel>()
        articles.forEach {
            mutableList.add(mapArticle(it))
        }
        return mutableList
    }

    private fun mapArticle(article: ArticleDTODomainModel): ArticleDTOModel {
        return ArticleDTOModel(
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

    private fun mapSource(source: SourceDTODomainModel?): SourceDTOModel {
        return SourceDTOModel(
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

    private fun mapSourceInfos(sourceInfos: List<SourceInfoDTODomainModel>): MutableList<SourceInfoDTOModel> {
        val mutableList = mutableListOf<SourceInfoDTOModel>()
        sourceInfos.forEach {
            mutableList.add(mapSourceInfo(it))
        }
        return mutableList
    }

    private fun mapSourceInfo(sourceInfoDTO: SourceInfoDTODomainModel): SourceInfoDTOModel {
        return SourceInfoDTOModel(
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