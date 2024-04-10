package com.example.aston_intensiv_final_project.data.model.news

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: MutableList<ArticleDTO>,
)