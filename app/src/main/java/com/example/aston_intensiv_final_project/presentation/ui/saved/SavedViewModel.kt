package com.example.aston_intensiv_final_project.presentation.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aston_intensiv_final_project.domain.usecase.GetSavedArticlesUseCase
import com.example.aston_intensiv_final_project.domain.usecase.GetSearchArticlesFromSavedUseCase
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedViewModel @Inject constructor(
    private val getSavedArticlesUseCase: GetSavedArticlesUseCase,
    private val getSearchArticlesFromSavedUseCase: GetSearchArticlesFromSavedUseCase,
    private val mapper: DomainToPresentationMapper
) : ViewModel() {

    private var _savedArticlesStateFlow = MutableStateFlow<List<ArticleModel>>(emptyList())
    val savedArticlesStateFlow: StateFlow<List<ArticleModel>> = _savedArticlesStateFlow

    private var _isLoadingStateFlow = MutableStateFlow<Boolean>(true)
    val isLoadingStateFlow: StateFlow<Boolean> = _isLoadingStateFlow

    init {
        getSavedArticles()
    }

    fun getSavedArticles() {
        _isLoadingStateFlow.value = true
        viewModelScope.launch {
            getSavedArticlesUseCase().map { dboArticlesList ->
                mapper.mapArticles(dboArticlesList.toMutableList())
            }.onEach { articleList ->
                _savedArticlesStateFlow.value = articleList
                _isLoadingStateFlow.value = false
            }.launchIn(viewModelScope)
        }
    }

    fun getSearchArticlesFromSaved(query: String) {
        _isLoadingStateFlow.value = true
        viewModelScope.launch {
            getSearchArticlesFromSavedUseCase(query).map {
                mapper.mapArticles(it.toMutableList())
            }.onEach {
                _savedArticlesStateFlow.value = it
                _isLoadingStateFlow.value = false
            }.launchIn(viewModelScope)
        }
    }

    fun refresh() {
        _savedArticlesStateFlow.value = emptyList()
    }

    class SavedViewModelFactory @Inject constructor(
        private val getSavedArticlesUseCase: GetSavedArticlesUseCase,
        private val getSearchArticlesFromSavedUseCase: GetSearchArticlesFromSavedUseCase,
        private val mapper: DomainToPresentationMapper
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SavedViewModel(
                getSavedArticlesUseCase = getSavedArticlesUseCase,
                getSearchArticlesFromSavedUseCase = getSearchArticlesFromSavedUseCase,
                mapper = mapper,
            ) as T
        }
    }

}