package com.example.aston_intensiv_final_project.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aston_intensiv_final_project.data.cache.model.SavedArticleDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveArticle(article: SavedArticleDbo)

    @Query("SELECT * FROM saved_articles")
    fun getSavedArticles(): Flow<List<SavedArticleDbo>>

    @Query("SELECT * FROM saved_articles WHERE sourceName = :sourceName AND title = :title")
    fun getSavedArticle(sourceName: String?, title: String?): Flow<SavedArticleDbo?>

    @Query("DELETE FROM saved_articles WHERE sourceName = :sourceName AND title = :title")
    fun deleteSavedArticle(sourceName: String?, title: String?)

    @Query("DELETE FROM saved_articles WHERE saved_time <= :date")
    fun deleteOldArticles(date: Long)

}