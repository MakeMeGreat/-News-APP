package com.example.aston_intensiv_final_project.presentation.headlines.filter

import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aston_intensiv_final_project.data.RepositoryImpl
import com.example.aston_intensiv_final_project.data.mapper.DataToDomainMapper
import com.example.aston_intensiv_final_project.data.network.NetworkDataSource
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale

class FiltersViewModel : ViewModel() {

    //Todo: it should be created by DI
    private val repository = RepositoryImpl(NetworkDataSource(), DataToDomainMapper())
    private val mapper = DomainToPresentationMapper()

    val filteredNews = MutableLiveData<NewsResponseModel>()

    val dateLiveData = MutableLiveData<Long>(0L)
    val languageLiveData = MutableLiveData<String>("en")
    val sortByLiveData = MutableLiveData<String?>(null)



   /* fun setDate(dateInMillis: Long) {
        dateLiveData.value = dateInMillis
    }*/

    fun getFormattedDateToQuery(): String? {
        if (dateLiveData.value == 0L) return null
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        return format.format(dateLiveData.value)
    }

}