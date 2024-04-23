package com.example.aston_intensiv_final_project.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aston_intensiv_final_project.domain.usecase.GetSavedArticlesUseCase
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleDtoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SavedViewModel @Inject constructor(
    //private val articleDao: SavedArticleDao,
    private val getSavedArticlesUseCase: GetSavedArticlesUseCase,
    private val mapper: DomainToPresentationMapper
) : ViewModel() {

    private val _savedArticlesStateFlow = MutableStateFlow<List<ArticleDtoModel>>(emptyList())
    val savedArticlesStateFlow: StateFlow<List<ArticleDtoModel>> = _savedArticlesStateFlow

//
//    private var savedArticlesJob: Job? = null
//    private fun setSavedArticlesStateFlow(value: List<ArticleDtoModel>) {
//        _savedArticlesStateFlow.value = value
//    }
    init {
        getConvertAndSet()
    }
//
//    fun convertFlowToMutableStateFlow(/*flow: Flow<List<ArticleDtoModel>>*/) {
//        savedArticlesJob?.cancel()
//        savedArticlesJob = artUseCase
//            .onEach { articlesList ->
//                setSavedArticlesStateFlow(articlesList)
//            }
//            .launchIn(viewModelScope)
//    }
    private fun getConvertAndSet() {
        getSavedArticles().onEach { articleList ->
            _savedArticlesStateFlow.value = articleList
        }
            .launchIn(viewModelScope)
    }

//    var articles = getSavedArticles()

    private fun getSavedArticles(): Flow<List<ArticleDtoModel>> {
        return getSavedArticlesUseCase.invoke().map { dboArticlesList ->
            mapper.mapArticles(dboArticlesList.toMutableList())
        }
    }
}