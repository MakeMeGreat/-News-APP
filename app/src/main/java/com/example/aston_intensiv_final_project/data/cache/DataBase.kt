package com.example.aston_intensiv_final_project.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aston_intensiv_final_project.data.cache.saved.SavedArticleDao
import com.example.aston_intensiv_final_project.data.cache.saved.model.SavedArticleDbo

@Database(
    entities = [
        SavedArticleDbo::class,
        CachedArticleDbo::class,
        CacheSourceDbo::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {

    abstract fun savedArticleDao(): SavedArticleDao
    abstract fun cachedArticleDao(): CacheArticleDao
    abstract fun cachedSourceDao(): CacheSourceDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null
        fun getDatabase(context: Context): DataBase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "saved_article_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}