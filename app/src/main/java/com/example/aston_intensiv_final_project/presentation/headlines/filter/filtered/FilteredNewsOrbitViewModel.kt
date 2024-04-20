package com.example.aston_intensiv_final_project.presentation.headlines.filter.filtered

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aston_intensiv_final_project.domain.model.news.NewsResponseDomainModel
import com.example.aston_intensiv_final_project.domain.usecase.GetFilteredNewsUseCase
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class FilteredNewsOrbitViewModel @AssistedInject constructor(
    @Assisted("date") private val date: String?,
    @Assisted("language") private val language: String,
    @Assisted("sortFilter") private val sortFilter: String?,
    private val getFilteredNewsUseCase: GetFilteredNewsUseCase,
    private val mapper: DomainToPresentationMapper
) : ContainerHost<FilteredNewsState, FilteredNewsSideEffect>,
    ViewModel() {

    override val container =
        container<FilteredNewsState, FilteredNewsSideEffect>(FilteredNewsState())

    init {
        getFilteredNews(date = date, language = language, sortFilter = sortFilter)
    }


    private fun getFilteredNews(date: String?, language: String, sortFilter: String?) = intent {
        reduce {
            state.copy(isLoading = true)
        }
        getFilteredNewsUseCase(
            from = date,
            language = language,
            sortBy = sortFilter
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<NewsResponseDomainModel>() {
                override fun onNext(response: NewsResponseDomainModel) {
                    val mappedResponse = mapper.mapNewsToPresentationModel(response)
                    viewModelScope.launch {
                        reduce {
                            state.copy(
                                isLoading = false,
                                filteredNewsResponse = mappedResponse,
                                error = null
                            )
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    Log.e("Filters", "problem in getting filtered news: $e")
                    viewModelScope.launch {
                        reduce {
                            state.copy(
                                isLoading = false,
                                error = "problem in getting filtered news: $e"
                            )
                        }
                    }
                }

                override fun onComplete() {}
            })
    }

    @AssistedFactory
    interface FilteredNewsViewModelFactory {
        fun create(
            @Assisted("date") date: String?,
            @Assisted("language") language: String,
            @Assisted("sortFilter") sortFilter: String?,
        ): FilteredNewsOrbitViewModel
    }
}