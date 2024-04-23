package com.example.aston_intensiv_final_project.data

import com.example.aston_intensiv_final_project.data.cached.CacheDataSource
import com.example.aston_intensiv_final_project.data.mapper.DataToDomainMapper
import com.example.aston_intensiv_final_project.data.mapper.DomainToDataMapper
import com.example.aston_intensiv_final_project.data.network.NetworkDataSource
import com.example.aston_intensiv_final_project.data.cached.saved.SavedArticleDao
import com.example.aston_intensiv_final_project.data.cached.saved.model.ArticleDbo
import com.example.aston_intensiv_final_project.domain.Repository
import com.example.aston_intensiv_final_project.domain.model.news.ArticleDtoDomainModel
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceResponseDomainModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val toDomainMapper: DataToDomainMapper,
    //private val savedArticleDao: SavedArticleDao,
    private val cacheDataSource: CacheDataSource,
    private val toDataMapper: DomainToDataMapper,
) : Repository {
    override fun getGeneralNews(pageNumber: Int): Observable<NewsResponseDomainModel> {
        return networkDataSource.getGeneralNews(pageNumber)
            .map {
                toDomainMapper.mapNewsToDomainModel(it)
            }
    }

    override fun getBusinessNews(pageNumber: Int): Observable<NewsResponseDomainModel> {
        return networkDataSource.getBusinessNews(pageNumber)
            .map {
                toDomainMapper.mapNewsToDomainModel(it)
            }
    }

    override fun getScienceNews(pageNumber: Int): Observable<NewsResponseDomainModel> {
        return networkDataSource.getScienceNews(pageNumber)
            .map {
                toDomainMapper.mapNewsToDomainModel(it)
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
        return networkDataSource.getFilteredNews(
            from = from,
            language = language,
            sortBy = sortBy
        ).map {
            toDomainMapper.mapNewsToDomainModel(it)
        }
    }

    override fun getSearchNews(searchQuery: String): Observable<NewsResponseDomainModel> {
        return networkDataSource.getSearchNews(searchQuery).map {
            toDomainMapper.mapNewsToDomainModel(it)
        }
    }

    override suspend fun saveOrDeleteArticle(articleDtoDomainModel: ArticleDtoDomainModel) {
        val mappedArticle = toDataMapper.mapArticle(articleDtoDomainModel)
        cacheDataSource.safeOrDeleteArticle(mappedArticle)
    }

    override fun getAllSavedArticles(): Flow<List<ArticleDtoDomainModel>> {
        return cacheDataSource.getAllSavedArticles().map { toDomainMapper.mapArticles(it.toMutableList()).toList() }
    }
}