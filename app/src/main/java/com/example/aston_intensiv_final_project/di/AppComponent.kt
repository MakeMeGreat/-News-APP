package com.example.aston_intensiv_final_project.di

import com.example.aston_intensiv_final_project.presentation.ui.headlines.businnes.HeadlinesBusinessFragment
import com.example.aston_intensiv_final_project.presentation.ui.headlines.filter.filtered.FilteredNewsFragment
import com.example.aston_intensiv_final_project.presentation.ui.headlines.general.HeadlinesGeneralFragment
import com.example.aston_intensiv_final_project.presentation.ui.headlines.traveling.HeadlinesTravelingFragment
import com.example.aston_intensiv_final_project.presentation.ui.newsprofile.NewsProfileFragment
import com.example.aston_intensiv_final_project.presentation.ui.saved.SavedNewsFragment
import com.example.aston_intensiv_final_project.presentation.ui.headlines.search.SearchFragment
import com.example.aston_intensiv_final_project.presentation.ui.sources.SourcesFragment
import com.example.aston_intensiv_final_project.presentation.ui.sources.onesource.OneSourceFragment
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