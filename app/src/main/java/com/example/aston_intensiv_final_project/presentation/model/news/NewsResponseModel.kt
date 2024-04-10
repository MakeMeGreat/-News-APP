package com.example.aston_intensiv_final_project.presentation.model.news

data class NewsResponseModel(
    val status: String,
    val totalResults: Int,
    val articles: MutableList<ArticleDTOModel>,
)