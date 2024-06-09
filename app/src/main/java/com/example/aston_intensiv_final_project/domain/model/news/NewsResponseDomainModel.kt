package com.example.aston_intensiv_final_project.domain.model.news


data class NewsResponseDomainModel(
    val status: String,
    val totalResults: Int,
    val articles: MutableList<ArticleDtoDomainModel>,
)
