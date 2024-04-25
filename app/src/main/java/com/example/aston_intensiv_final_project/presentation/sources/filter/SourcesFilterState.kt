package com.example.aston_intensiv_final_project.presentation.sources.filter

data class SourcesFilterState(
    val categoryFilter: Int,
    val languageFilter: Int
)

sealed class SourcesFilterSideEffect {
    data class SideEffectWrapper(val text: String) : SourcesFilterSideEffect()
}