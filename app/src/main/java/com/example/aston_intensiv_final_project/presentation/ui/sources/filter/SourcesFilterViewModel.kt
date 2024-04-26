package com.example.aston_intensiv_final_project.presentation.ui.sources.filter

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class SourcesFilterViewModel :
    ContainerHost<SourcesFilterState, SourcesFilterSideEffect>,
    ViewModel() {

    override val container = container<SourcesFilterState, SourcesFilterSideEffect>(
        SourcesFilterState(-1, -1)
    )

    fun sendEvent(event: SourceFilterEvent) = intent {
        reduce {
            when (event) {
                is SourceFilterEvent.UpdateCategoryEvent -> {
                    state.copy(categoryFilter = event.categoryChipId)
                }

                is SourceFilterEvent.UpdateLanguageEvent -> state.copy(languageFilter = event.languageChipId)
            }
        }
    }
}