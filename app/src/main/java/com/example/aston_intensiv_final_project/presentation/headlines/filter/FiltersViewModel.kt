package com.example.aston_intensiv_final_project.presentation.headlines.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FiltersViewModel : ViewModel() {

    // Todo: it should be created by DI
//    private val repository = RepositoryImpl(NetworkDataSource(), DataToDomainMapper())
//    private val mapper = DomainToPresentationMapper()
//
//    val filteredNews = MutableLiveData<NewsResponseModel>()

//    val dateLiveData = MutableLiveData<Long>(0L)
//    val languageLiveData = MutableLiveData<String>("en")
//    val sortByLiveData = MutableLiveData<String?>(null)

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


   /* fun setDate(dateInMillis: Long) {
        dateLiveData.value = dateInMillis
    }*/

//    fun getFormattedDateToQuery(): String? {
//        if (dateLiveData.value == 0L) return null
//        val format = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
//        return format.format(dateLiveData.value)
//    }

}