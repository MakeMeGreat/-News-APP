package com.example.aston_intensiv_final_project.data.cache

import android.os.Build
import com.example.aston_intensiv_final_project.data.cache.mapper.ToArticleDtoMapper
import com.example.aston_intensiv_final_project.data.cache.saved.SavedArticleDao
import com.example.aston_intensiv_final_project.data.cache.saved.model.SavedArticleDbo
import com.example.aston_intensiv_final_project.data.model.news.ArticleDto
import com.example.aston_intensiv_final_project.data.model.news.NewsResponse
import com.example.aston_intensiv_final_project.data.model.news.SourceDto
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale
import javax.inject.Inject

class CacheDataSource @Inject constructor(
    private val mapper: ToArticleDtoMapper,
    private val savedArticleDao: SavedArticleDao,
    private val cacheArticleDao: CacheArticleDao,

    ) {
    suspend fun saveOrDeleteArticle(articleDto: ArticleDto) {
        val dataBaseArticle = SavedArticleDbo(
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
        val article = savedArticleDao.getSavedArticle(
            sourceName = dataBaseArticle.sourceName,
            title = dataBaseArticle.title,
        )
        if (article.firstOrNull() == null) {
            savedArticleDao.saveArticle(dataBaseArticle)
        } else {
            savedArticleDao.deleteSavedArticle(dataBaseArticle.sourceName, dataBaseArticle.title)
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
        savedArticleDao.deleteOldArticles(System.currentTimeMillis() - 14 * 24 * 60 * 60 * 1000)
    }

    fun getCategorizedNews(category: String, pageNumber: Int): Observable<NewsResponse> {
//        val mList = mutableListOf<ArticleDto>()
//        val cachedNews = cacheArticleDao.getCategorizedNews(
//            category = category,
//            //pageNumber = pageNumber
//        ).doOnNext {
//            mList.addAll(0, mapper.mapToListOfArticleDto(it))
//        }.blockingFirst()
//        val count = 0
//
//        return Observable.create{ o ->
//            o.onNext(NewsResponse(
//                status = "fromCache",
//                totalResults = count,
//                articles = mList
//            ))
//        }
        return cacheArticleDao.getCategorizedNews(category = category)
            .map { articles ->
                NewsResponse(
                    status = "fromCache",
                    totalResults = 0,
                    articles = mapper.mapToListOfArticleDto(articles)
                )
            }
    }

    fun getFilteredNews(
        from: String?,
        language: String?,
        sortBy: String?
    ): Observable<NewsResponse> {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fromDateInMillis =
            if (from == null) 0L
            else formatter.parse(from)?.time ?: 0L// throws exception

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return cacheArticleDao.getAllSavedNews().map {
                it.filter {
                    Instant.parse(it.publishedAt).toEpochMilli() > fromDateInMillis
                }
            }.map { articles ->
                NewsResponse(
                    status = "fromCache",
                    totalResults = articles.size,
                    articles = mapper.mapToListOfArticleDto(articles)
                )
            }
        } else {
            return cacheArticleDao.getAllSavedNews().map {
                it.filter {
                    formatDateToMilliseconds(it.publishedAt!!) > fromDateInMillis
                }
            }.map { articles ->
                NewsResponse(
                    status = "fromCache",
                    totalResults = articles.size,
                    articles = mapper.mapToListOfArticleDto(articles)
                )
            }
        }
    }

    fun getSearchNews(query: String): Observable<NewsResponse> {
        return cacheArticleDao.getSearchNews(query)
            .map { articles ->
                NewsResponse(
                    status = "fromCache",
                    totalResults = articles.size,
                    articles = mapper.mapToListOfArticleDto(articles)
                )
            }
    }

    private fun formatDateToMilliseconds(dateFormat: String): Long {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return try {
            formatter.parse(dateFormat)?.time ?: 0L
        } catch (e: ParseException) {
            0L
        }

    }
}