package com.example.aston_intensiv_final_project.data.network

import com.example.aston_intensiv_final_project.data.cache.CacheArticleDao
import com.example.aston_intensiv_final_project.data.cache.mapper.ToCachedArticleDboMapper
import com.example.aston_intensiv_final_project.data.model.news.NewsResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val cacheArticleDao: CacheArticleDao,
    private val mapper: ToCachedArticleDboMapper
) {

    fun getCategorizedNews(category: String, pageNumber: Int): Observable<NewsResponse> {
        return RetrofitObject.retrofitService.getCategorizedNews(
            category = category,
            pageNumber = pageNumber
        )
            .doOnNext { newsResponse ->
                newsResponse.articles.forEach {
                    cacheArticleDao.cacheArticle(mapper.mapToCache(it, category, pageNumber))
                }
            }
            .subscribeOn(Schedulers.io())
    }


    fun getSources(language: String, category: String) =
        RetrofitObject.retrofitService.getSources(
            language = language,
            category = category,
        )

    fun getOneSourceNews(sourceId: String) =
        RetrofitObject.retrofitService.getOneSourceNews(sourceId = sourceId)

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
                cacheArticleDao.cacheArticle(mapper.mapToCache(it))
            }
        }
            .subscribeOn(Schedulers.io())

    fun getSearchNews(
        searchQuery: String
    ) = RetrofitObject.retrofitService.getSearchNews(q = searchQuery).doOnNext { newsResponse ->
        newsResponse.articles.forEach {
            cacheArticleDao.cacheArticle(mapper.mapToCache(it))
        }
    }
        .subscribeOn(Schedulers.io())
}