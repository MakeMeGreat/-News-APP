package com.example.aston_intensiv_final_project.presentation.ui.headlines.filter.filtered

import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel

data class FilteredNewsState(
    val filteredNewsResponse: NewsResponseModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class FilteredNewsSideEffect {
    data class WrapperSideEffect(val text: String) : FilteredNewsSideEffect()
}