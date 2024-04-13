package com.example.aston_intensiv_final_project.data

import com.example.aston_intensiv_final_project.data.mapper.DataToDomainMapper
import com.example.aston_intensiv_final_project.data.network.NetworkDataSource
import com.example.aston_intensiv_final_project.domain.Repository
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.model.source.SourceResponseDomainModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val mapper: DataToDomainMapper
) : Repository {
    override fun getGeneralNews(pageNumber: Int): Observable<NewsResponseDomainModel> {
        return networkDataSource.getGeneralNews(pageNumber)
            .map {
                mapper.mapNewsToDomainModel(it)
            }
    }

    override fun getBusinessNews(pageNumber: Int): Observable<NewsResponseDomainModel> {
        return networkDataSource.getBusinessNews(pageNumber)
            .map {
                mapper.mapNewsToDomainModel(it)
            }
    }

    override fun getScienceNews(pageNumber: Int): Observable<NewsResponseDomainModel> {
        return networkDataSource.getScienceNews(pageNumber)
            .map {
                mapper.mapNewsToDomainModel(it)
            }
    }

    override fun getSources(): Observable<SourceResponseDomainModel> {
        return networkDataSource.getSources()
            .map {
                mapper.mapSourcesToDomainModel(it)
            }
    }

    override fun getOneSourceNews(sourceId: String): Observable<NewsResponseDomainModel> {
        return networkDataSource.getOneSourceNews(sourceId)
            .map {
                mapper.mapNewsToDomainModel(it)
            }
    }
}