package com.example.aston_intensiv_final_project.presentation.di

import android.app.Application
import android.content.Context
import com.example.aston_intensiv_final_project.data.cached.saved.SavedArticleDao
import com.example.aston_intensiv_final_project.data.cached.ArticleDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(val application: Application) {

    @Singleton
    @Provides
    fun getSavedArticleDao(savedArticleDataBase: ArticleDataBase): SavedArticleDao {
        return savedArticleDataBase.savedArticleDao()
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