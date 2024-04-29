package com.example.aston_intensiv_final_project.presentation.model.news

import java.io.Serializable

data class NewsResponseModel(
    val status: String,
    val totalResults: Int,
    val articles: MutableList<ArticleModel>,
) : Serializable