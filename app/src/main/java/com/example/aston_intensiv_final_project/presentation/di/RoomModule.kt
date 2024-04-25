package com.example.aston_intensiv_final_project.presentation.di

import android.app.Application
import android.content.Context
import com.example.aston_intensiv_final_project.data.cache.ArticleDataBase
import com.example.aston_intensiv_final_project.data.cache.CacheArticleDao
import com.example.aston_intensiv_final_project.data.cache.saved.SavedArticleDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(private val application: Application) {

    @Singleton
    @Provides
    fun getSavedArticleDao(savedArticleDataBase: ArticleDataBase): SavedArticleDao {
        return savedArticleDataBase.savedArticleDao()
    }

    @Singleton
    @Provides
    fun getCachedArticleDao(savedArticleDataBase: ArticleDataBase): CacheArticleDao {
        return savedArticleDataBase.cachedArticleDao()
    }

    @Singleton
    @Provides
    fun provideSavedArticleDataBase(): ArticleDataBase {
        return ArticleDataBase.getDatabase(provideContext())
    }

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application.applicationContext
    }
}