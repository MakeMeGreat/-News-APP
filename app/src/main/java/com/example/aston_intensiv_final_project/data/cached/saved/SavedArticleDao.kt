package com.example.aston_intensiv_final_project.data.cached.saved

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aston_intensiv_final_project.data.cached.saved.model.ArticleDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: ArticleDbo)

//    @Delete
//    suspend fun delete(article: ArticleDbo)

    @Query("SELECT * FROM saved_articles")
    fun getSavedArticles(): Flow<List<ArticleDbo>>

    @Query("SELECT * FROM saved_articles WHERE sourceName = :sourceName AND title = :title")
    fun getArticle(sourceName: String?, title: String?): Flow<ArticleDbo?>

    @Query("DELETE FROM saved_articles WHERE sourceName = :sourceName AND title = :title")
    fun deleteArticle(sourceName: String?, title: String?)


//    @Query("SELECT * FROM saved_articles WHERE id = :id")
//    fun getSavedArticle(id: Int): Flow<ArticleDbo>
}