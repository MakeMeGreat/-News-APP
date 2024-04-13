package com.example.aston_intensiv_final_project.presentation.di

import com.example.aston_intensiv_final_project.presentation.headlines.businnes.HeadlinesBusinessFragment
import com.example.aston_intensiv_final_project.presentation.headlines.general.HeadlinesGeneralFragment
import com.example.aston_intensiv_final_project.presentation.headlines.traveling.HeadlinesTravelingFragment
import com.example.aston_intensiv_final_project.presentation.sources.SourcesFragment
import com.example.aston_intensiv_final_project.presentation.sources.onesource.OneSourceFragment
import dagger.Component


@Component(
    modules = [RepositoryModule::class]
)
interface AppComponent {

    fun inject(fragment: SourcesFragment)
    fun inject(fragment: OneSourceFragment)
    fun inject(fragment: HeadlinesBusinessFragment)
    fun inject(fragment: HeadlinesGeneralFragment)
    fun inject(fragment: HeadlinesTravelingFragment)
}