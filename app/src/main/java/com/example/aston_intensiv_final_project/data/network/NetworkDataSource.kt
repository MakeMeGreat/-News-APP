package com.example.aston_intensiv_final_project.data.network

import com.example.aston_intensiv_final_project.data.cache.CacheArticleDao
import com.example.aston_intensiv_final_project.data.cache.CacheSourceDao
import com.example.aston_intensiv_final_project.data.cache.mapper.ToDboMapper
import com.example.aston_intensiv_final_project.data.model.news.NewsResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val cacheArticleDao: CacheArticleDao,
    private val cacheSourceDao: CacheSourceDao,
    private val mapper: ToDboMapper
) {

    fun getCategorizedNews(category: String, pageNumber: Int): Observable<NewsResponse> {
        return RetrofitObject.retrofitService.getCategorizedNews(
            category = category,
            pageNumber = pageNumber
        )
            .doOnNext { newsResponse ->
                newsResponse.articles.forEach {
                    cacheArticleDao.cacheArticle(mapper.mapArticle(it, category))
                }
            }
            .subscribeOn(Schedulers.io())
    }


    fun getSources(language: String, category: String) =
        RetrofitObject.retrofitService.getSources(
            language = language,
            category = category,
        )
            .doOnNext { sourceResponse ->
                sourceResponse.sources.forEach {
                    cacheSourceDao.cacheSource(mapper.mapSource(it))
                }
            }

    fun getOneSourceNews(sourceId: String) =
        RetrofitObject.retrofitService.getOneSourceNews(sourceId = sourceId)
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
        RetrofitObject.retrofitService.getFilteredNews(
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
    ) = RetrofitObject.retrofitService.getSearchNews(q = searchQuery).doOnNext { newsResponse ->
        newsResponse.articles.forEach {
            cacheArticleDao.cacheArticle(mapper.mapArticle(it))
        }
    }
        .subscribeOn(Schedulers.io())
}