package com.example.aston_intensiv_final_project.presentation.di

import com.example.aston_intensiv_final_project.presentation.headlines.businnes.HeadlinesBusinessFragment
import com.example.aston_intensiv_final_project.presentation.headlines.filter.filtered.FilteredNewsFragment
import com.example.aston_intensiv_final_project.presentation.headlines.general.HeadlinesGeneralFragment
import com.example.aston_intensiv_final_project.presentation.headlines.traveling.HeadlinesTravelingFragment
import com.example.aston_intensiv_final_project.presentation.newsprofile.NewsProfileFragment
import com.example.aston_intensiv_final_project.presentation.saved.SavedNewsFragment
import com.example.aston_intensiv_final_project.presentation.search.SearchFragment
import com.example.aston_intensiv_final_project.presentation.sources.SourcesFragment
import com.example.aston_intensiv_final_project.presentation.sources.onesource.OneSourceFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class, RoomModule::class]
)
interface AppComponent {

    fun inject(fragment: SourcesFragment)
    fun inject(fragment: OneSourceFragment)
    fun inject(fragment: HeadlinesBusinessFragment)
    fun inject(fragment: HeadlinesGeneralFragment)
    fun inject(fragment: HeadlinesTravelingFragment)
    fun inject(fragment: FilteredNewsFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: NewsProfileFragment)
    fun inject(fragment: SavedNewsFragment)

}