package com.example.aston_intensiv_final_project.data.model.news


data class ArticleDTO(
    val source: SourceDTO?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)