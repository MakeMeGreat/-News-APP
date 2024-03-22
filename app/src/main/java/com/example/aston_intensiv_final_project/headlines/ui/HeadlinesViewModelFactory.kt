package com.example.aston_intensiv_final_project.headlines.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aston_intensiv_final_project.headlines.data.repository.NewsRepository

class HeadlinesViewModelFactory(
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HeadlinesViewModel(newsRepository) as T
    }
}