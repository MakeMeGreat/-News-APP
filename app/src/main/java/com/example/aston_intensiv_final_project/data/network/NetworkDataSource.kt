package com.example.aston_intensiv_final_project.data.network

import com.example.aston_intensiv_final_project.data.cache.dao.CacheArticleDao
import com.example.aston_intensiv_final_project.data.cache.dao.CacheSourceDao
import com.example.aston_intensiv_final_project.data.cache.mapper.ToDboMapper
import com.example.aston_intensiv_final_project.data.model.news.NewsResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val retrofitService: NewsAPI,
    private val cacheArticleDao: CacheArticleDao,
    private val cacheSourceDao: CacheSourceDao,
    private val mapper: ToDboMapper
) {

    fun getCategorizedNews(category: String, pageNumber: Int): Observable<NewsResponse> {
        return retrofitService.getCategorizedNews(
            category = category,
            pageNumber = pageNumber
        )
            .doOnNext { newsResponse ->
                newsResponse.articles.forEach {
                    cacheArticleDao.cacheArticle(mapper.mapArticle(it, category))
                }
            }
    }


    fun getSources(language: String, category: String) =
        retrofitService.getSources(
            language = language,
            category = category,
        )
            .doOnNext { sourceResponse ->
                sourceResponse.sources.forEach {
                    cacheSourceDao.cacheSource(mapper.mapSource(it))
                }
            }

    fun getOneSourceNews(sourceId: String) =
        retrofitService.getOneSourceNews(sourceId = sourceId)
            .doOnNext { newsResponse ->
                newsResponse.articles.forEach {
                    cacheArticleDao.cacheArticle(mapper.mapArticle(it))
                }
            }

    fun getFilteredNews(
        from: String?,
        language: String?,
        sortBy: String?
    ) =
        retrofitService.getFilteredNews(
            from = from,
            language = language,
            sortBy = sortBy
        ).doOnNext { newsResponse ->
            newsResponse.articles.forEach {
                cacheArticleDao.cacheArticle(mapper.mapArticle(it))
            }
        }
            .subscribeOn(Schedulers.io())

    fun getSearchNews(
        searchQuery: String
    ) = retrofitService.getSearchNews(q = searchQuery).doOnNext { newsResponse ->
        newsResponse.articles.forEach {
            cacheArticleDao.cacheArticle(mapper.mapArticle(it))
        }
    }
        .subscribeOn(Schedulers.io())
}