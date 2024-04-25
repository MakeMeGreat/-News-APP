package com.example.aston_intensiv_final_project.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_sources")
data class CacheSourceDbo(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String?,
    val description: String?,
    val url: String?,
    val category: String?,
    val language: String?,
    val country: String?,
)