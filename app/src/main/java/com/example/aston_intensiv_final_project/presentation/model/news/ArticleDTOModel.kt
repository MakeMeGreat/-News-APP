package com.example.aston_intensiv_final_project.presentation.model.news

data class ArticleDTOModel(
    val source: SourceDTOModel?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)