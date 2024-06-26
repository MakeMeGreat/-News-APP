package com.example.aston_intensiv_final_project.presentation.ui.newsprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aston_intensiv_final_project.domain.usecase.SaveOrDeleteArticleUseCase
import com.example.aston_intensiv_final_project.presentation.mapper.PresentationToDomainMapper
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsProfileViewModel @AssistedInject constructor(
    @Assisted("article") private val article: ArticleModel,
    private val saveOrDeleteArticleUseCase: SaveOrDeleteArticleUseCase,
    private val presentationToDomainMapper: PresentationToDomainMapper
) : ViewModel() {
    private val _articleFlow = MutableStateFlow<ArticleModel>(article)
    val articleFlow: StateFlow<ArticleModel> = _articleFlow

    fun insertOrDelete() {
        viewModelScope.launch(Dispatchers.IO) {
            saveOrDeleteArticleUseCase(presentationToDomainMapper.mapArticle(article))
        }
    }

    @AssistedFactory
    interface NewsProfileViewModelFactory {
        fun create(
            @Assisted("article") article: ArticleModel,
        ): NewsProfileViewModel
    }
}