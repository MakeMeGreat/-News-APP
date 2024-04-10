package com.example.aston_intensiv_final_project.headlines.data.models


data class NewsResponse (
    val status: String,
    val totalResults: Int,
    val articles: MutableList<ArticleDTO>,
)