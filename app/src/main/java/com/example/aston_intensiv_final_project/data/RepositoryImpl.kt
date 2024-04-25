package com.example.aston_intensiv_final_project.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.aston_intensiv_final_project.data.cache.CacheDataSource
import com.example.aston_intensiv_final_project.data.mapper.DataToDomainMapper
import com.example.aston_intensiv_final_project.data.mapper.DomainToDataMapper
import com.example.aston_intensiv_final_project.data.network.NetworkDataSource
import com.example.aston_intensiv_final_project.domain.Repository
import com.example.aston_intensiv_final_project.domain.model.news.ArticleDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceResponseDomainModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val cacheDataSource: CacheDataSource,
    private val toDomainMapper: DataToDomainMapper,
    private val toDataMapper: DomainToDataMapper,
    private val context: Context,
) : Repository {


    override fun getCategorizedNews(
        category: String,
        pageNumber: Int
    ): Observable<NewsResponseDomainModel> {
        return if (isInternetAvailable(context)) {
            networkDataSource.getCategorizedNews(
                category = category,
                pageNumber = pageNumber
            )
                .map {
                    toDomainMapper.mapNewsToDomainModel(it)
                }
        } else {
            return cacheDataSource.getCategorizedNews(category = category, pageNumber = pageNumber)
                .map { toDomainMapper.mapNewsToDomainModel(it) }
        }

    }

    override fun getSources(): Observable<SourceResponseDomainModel> {
        return networkDataSource.getSources()
            .map {
                toDomainMapper.mapSourcesToDomainModel(it)
            }
    }

    override fun getOneSourceNews(sourceId: String): Observable<NewsResponseDomainModel> {
        return networkDataSource.getOneSourceNews(sourceId)
            .map {
                toDomainMapper.mapNewsToDomainModel(it)
            }
    }

    override fun getFilteredNews(
        from: String?,
        language: String?,
        sortBy: String?,
    ): Observable<NewsResponseDomainModel> {
        return if (isInternetAvailable(context)) {
            networkDataSource.getFilteredNews(
                from = from,
                language = language,
                sortBy = sortBy
            ).map {
                toDomainMapper.mapNewsToDomainModel(it)
            }
        } else {
            cacheDataSource.getFilteredNews(
                from = from,
                language = language,
                sortBy = sortBy
            ).map {
                toDomainMapper.mapNewsToDomainModel(it)
            }
        }
    }

    override fun getSearchNews(searchQuery: String): Observable<NewsResponseDomainModel> {
        return if (isInternetAvailable(context)) {
            networkDataSource.getSearchNews(searchQuery).map {
                toDomainMapper.mapNewsToDomainModel(it)
            }
        } else {
            cacheDataSource.getSearchNews(searchQuery).map {
                toDomainMapper.mapNewsToDomainModel(it)
            }
        }
    }

    override suspend fun saveOrDeleteArticle(articleDtoDomainModel: ArticleDtoDomainModel) {
        val mappedArticle = toDataMapper.mapArticle(articleDtoDomainModel)
        cacheDataSource.saveOrDeleteArticle(mappedArticle)
    }

    override fun getSavedArticles(): Flow<List<ArticleDtoDomainModel>> {
        return cacheDataSource.getSavedArticles()
            .map { toDomainMapper.mapArticles(it.toMutableList()).toList() }
    }


    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }
}