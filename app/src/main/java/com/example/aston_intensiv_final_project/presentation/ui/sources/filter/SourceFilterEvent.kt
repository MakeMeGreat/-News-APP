package com.example.aston_intensiv_final_project.presentation.ui.sources.filter

sealed class SourceFilterEvent {
    class UpdateCategoryEvent(val categoryChipId: Int) : SourceFilterEvent()
    class UpdateLanguageEvent(val languageChipId: Int) : SourceFilterEvent()
}