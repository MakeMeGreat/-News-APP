package com.example.aston_intensiv_final_project.data.cached

import com.example.aston_intensiv_final_project.data.cached.saved.SavedArticleDao
import com.example.aston_intensiv_final_project.data.cached.saved.model.ArticleDbo
import com.example.aston_intensiv_final_project.data.model.news.ArticleDto
import com.example.aston_intensiv_final_project.data.model.news.SourceDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheDataSource @Inject constructor(
    private val savedArticleDao: SavedArticleDao,
) {
    suspend fun safeOrDeleteArticle(articleDto: ArticleDto) {
        val dataBaseArticle = ArticleDbo(
            sourceId = articleDto.source?.id,
            sourceName = articleDto.source?.name,
            author = articleDto.author,
            title = articleDto.title,
            description = articleDto.description,
            url = articleDto.url,
            urlToImage = articleDto.urlToImage,
            publishedAt = articleDto.publishedAt,
            content = articleDto.content,
            savedTime = System.currentTimeMillis(),
        )
        val article = savedArticleDao.getArticle(
            sourceName = dataBaseArticle.sourceName,
            title = dataBaseArticle.title,
        )
        if (article.firstOrNull() == null) {
            savedArticleDao.insert(dataBaseArticle)
        } else {
            savedArticleDao.deleteArticle(dataBaseArticle.sourceName, dataBaseArticle.title)
        }
    }

    fun getSavedArticles(): Flow<List<ArticleDto>> {
        deleteOldArticles()
        val dataBaseObjects = savedArticleDao.getSavedArticles()
        val result: Flow<List<ArticleDto>> = dataBaseObjects.map { listOfDboArticles ->
            listOfDboArticles.map { articleDbo ->
                ArticleDto(
                    source = SourceDto(id = articleDbo.sourceId, name = articleDbo.sourceName),
                    author = articleDbo.author,
                    title = articleDbo.title,
                    description = articleDbo.description,
                    url = articleDbo.url,
                    urlToImage = articleDbo.urlToImage,
                    publishedAt = articleDbo.publishedAt,
                    content = articleDbo.content
                )
            }
        }
        return result
    }

    private fun deleteOldArticles() {
        savedArticleDao.deleteOldArticles(System.currentTimeMillis() -  14 * 24 * 60 * 60 * 1000)
    }
}