package com.example.aston_intensiv_final_project.presentation.sources.filter

sealed class SourceFilterEvent {
    class UpdateCategoryEvent(val categoryChipId: Int) : SourceFilterEvent()
    class UpdateLanguageEvent(val languageChipId: Int) : SourceFilterEvent()
}