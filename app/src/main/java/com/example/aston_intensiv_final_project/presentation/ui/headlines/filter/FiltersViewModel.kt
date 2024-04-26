package com.example.aston_intensiv_final_project.presentation.ui.headlines.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FiltersViewModel : ViewModel() {

    private val _state = MutableLiveData<FilterState>(FilterState("", 0L, "en"))
    val state: LiveData<FilterState> = _state

    fun sendEvent(event: FilterEvent) {
        when (event) {
            is FilterEvent.UpdateFilterEvent -> {
                _state.value = _state.value?.copy(filter = event.filter)
            }

            is FilterEvent.UpdateDateEvent -> {
                _state.value = _state.value?.copy(date = event.dateInMillis)
            }

            is FilterEvent.UpdateLanguageEvent -> {
                _state.value = _state.value?.copy(language = event.language)
            }
        }
    }

}