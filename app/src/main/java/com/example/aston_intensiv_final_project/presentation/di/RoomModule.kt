package com.example.aston_intensiv_final_project.presentation.di

import android.app.Application
import android.content.Context
import com.example.aston_intensiv_final_project.data.cached.saved.SavedArticleDao
import com.example.aston_intensiv_final_project.data.cached.saved.SavedArticleDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(val application: Application) {

    @Singleton
    @Provides
    fun getSavedArticleDao(savedArticleDataBase: SavedArticleDataBase): SavedArticleDao {
        return savedArticleDataBase.savedArticleDao()
    }

    @Singleton
    @Provides
    fun provideSavedArticleDataBase(): SavedArticleDataBase {
        return SavedArticleDataBase.getDatabase(provideContext())
    }

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application.applicationContext
    }
}