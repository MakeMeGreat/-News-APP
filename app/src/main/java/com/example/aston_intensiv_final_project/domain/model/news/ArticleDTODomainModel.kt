package com.example.aston_intensiv_final_project.domain.model.news


data class ArticleDTODomainModel(
    val source: SourceDTODomainModel?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)
