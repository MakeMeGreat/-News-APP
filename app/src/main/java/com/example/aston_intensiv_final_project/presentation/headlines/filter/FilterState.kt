package com.example.aston_intensiv_final_project.presentation.headlines.filter

data class FilterState (
    val filter: String = "",
    val date: Long = 0L,
    val language: String = "en"
)

sealed class FilterSideEffect{
    data class Wrapper(val text: String) : FilterSideEffect()
}