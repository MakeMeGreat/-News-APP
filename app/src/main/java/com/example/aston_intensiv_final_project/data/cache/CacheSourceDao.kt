package com.example.aston_intensiv_final_project.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Observable

@Dao
interface CacheSourceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun cacheSource(source: CacheSourceDbo)

    @Query(
        "SELECT * FROM cached_sources " +
                "WHERE (:category = '' OR category = :category) " +
                "AND (:language = '' OR language = :language) "
    )
    fun getSources(category: String, language: String): Observable<List<CacheSourceDbo>>

    @Query("DELETE FROM cached_sources")
    fun clearTable()

}