package com.example.aston_intensiv_final_project.presentation.ui.headlines.filter


sealed class FilterEvent {
    class UpdateFilterEvent(val filter: String) : FilterEvent()
    class UpdateDateEvent(val dateInMillis: Long) : FilterEvent()
    class UpdateLanguageEvent(val language: String) : FilterEvent()
}