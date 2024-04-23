package com.example.aston_intensiv_final_project.presentation.newsprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aston_intensiv_final_project.domain.usecase.SaveOrDeleteArticleUseCase
import com.example.aston_intensiv_final_project.presentation.mapper.PresentationToDomainMapper
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleDtoModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//todo change dao to useCase

class NewsProfileViewModel @AssistedInject constructor(
    @Assisted("article") private val article: ArticleDtoModel,
 //   private val articleDao: SavedArticleDao
    private val saveOrDeleteArticleUseCase: SaveOrDeleteArticleUseCase,
    private val presentationToDomainMapper: PresentationToDomainMapper
) : ViewModel() {
    private val _articleFlow = MutableStateFlow<ArticleDtoModel>(article)
    val articleFlow: StateFlow<ArticleDtoModel> = _articleFlow

    private val _isItSaved = MutableStateFlow<Boolean>(false)
    val isItSaved: StateFlow<Boolean> = _isItSaved

    fun insert() {
        viewModelScope.launch(Dispatchers.IO) {
            saveOrDeleteArticleUseCase.invoke(presentationToDomainMapper.mapArticle(article))
        }
    }

    @AssistedFactory
    interface NewsProfileViewModelFactory {
        fun create(
            @Assisted("article") article: ArticleDtoModel,
        ): NewsProfileViewModel
    }
}