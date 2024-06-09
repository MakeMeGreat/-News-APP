package com.example.aston_intensiv_final_project.domain.model.news


data class ArticleDtoDomainModel(
    val source: SourceDtoDomainModel?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)
