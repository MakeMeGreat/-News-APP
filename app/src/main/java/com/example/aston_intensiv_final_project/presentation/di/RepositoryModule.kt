package com.example.aston_intensiv_final_project.presentation.di

import com.example.aston_intensiv_final_project.data.RepositoryImpl
import com.example.aston_intensiv_final_project.domain.Repository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl): Repository

}