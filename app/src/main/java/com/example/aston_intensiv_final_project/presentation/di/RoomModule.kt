package com.example.aston_intensiv_final_project.presentation.di

import android.app.Application
import android.content.Context
import com.example.aston_intensiv_final_project.data.cache.CacheArticleDao
import com.example.aston_intensiv_final_project.data.cache.CacheSourceDao
import com.example.aston_intensiv_final_project.data.cache.DataBase
import com.example.aston_intensiv_final_project.data.cache.saved.SavedArticleDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(private val application: Application) {

    @Singleton
    @Provides
    fun getSavedArticleDao(savedDataBase: DataBase): SavedArticleDao {
        return savedDataBase.savedArticleDao()
    }

    @Singleton
    @Provides
    fun getCachedArticleDao(savedDataBase: DataBase): CacheArticleDao {
        return savedDataBase.cachedArticleDao()
    }

    @Singleton
    @Provides
    fun getCachedSourceDao(savedDataBase: DataBase): CacheSourceDao {
        return savedDataBase.cachedSourceDao()
    }

    @Singleton
    @Provides
    fun provideSavedArticleDataBase(): DataBase {
        return DataBase.getDatabase(provideContext())
    }

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application.applicationContext
    }
}