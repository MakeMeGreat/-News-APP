package com.example.aston_intensiv_final_project.presentation.model.news

import java.io.Serializable

data class ArticleModel(
    val source: SourceModel?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
) : Serializable