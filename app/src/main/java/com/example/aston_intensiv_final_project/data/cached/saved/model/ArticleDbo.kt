package com.example.aston_intensiv_final_project.data.cached.saved.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_articles")
data class ArticleDbo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sourceId: String?,
    val sourceName: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    @ColumnInfo("saved_time") val savedTime: Long?,
)
