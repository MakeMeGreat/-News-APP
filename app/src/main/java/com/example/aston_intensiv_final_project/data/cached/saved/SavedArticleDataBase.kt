package com.example.aston_intensiv_final_project.data.cached.saved

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aston_intensiv_final_project.data.cached.saved.model.ArticleDbo

@Database(entities = [ArticleDbo::class], version = 1, exportSchema = false)
abstract class SavedArticleDataBase : RoomDatabase() {

    abstract fun savedArticleDao(): SavedArticleDao

    companion object{
        @Volatile
        private var INSTANCE: SavedArticleDataBase? = null
        fun getDatabase(context: Context) : SavedArticleDataBase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, SavedArticleDataBase::class.java, "saved_article_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}