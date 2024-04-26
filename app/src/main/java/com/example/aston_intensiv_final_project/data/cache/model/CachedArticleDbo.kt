package com.example.aston_intensiv_final_project.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cached_articles")
data class CachedArticleDbo(
    val sourceId: String?,
    val sourceName: String?,
    val author: String?,
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val category: String? = null,
)
