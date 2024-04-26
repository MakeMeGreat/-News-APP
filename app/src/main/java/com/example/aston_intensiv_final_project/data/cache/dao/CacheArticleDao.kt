package com.example.aston_intensiv_final_project.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aston_intensiv_final_project.data.cache.model.CachedArticleDbo
import io.reactivex.rxjava3.core.Observable

@Dao
interface CacheArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun cacheArticle(article: CachedArticleDbo)

    @Query("SELECT * FROM cached_articles WHERE category = :category")
    fun getCategorizedNews(category: String): Observable<List<CachedArticleDbo>>

    @Query("select * from cached_articles where title like '%' || :query || '%' or description like '%' || :query || '%' or content like '%' || :query || '%'")
    fun getSearchNews(query: String): Observable<List<CachedArticleDbo>>

    @Query("SELECT * FROM cached_articles")
    fun getAllSavedNews(): Observable<List<CachedArticleDbo>>

    @Query("SELECT * FROM cached_articles where sourceId = :sourceId")
    fun getOneSourceNews(sourceId: String): Observable<List<CachedArticleDbo>>

    @Query("DELETE FROM cached_articles")
    fun clearTable()
}