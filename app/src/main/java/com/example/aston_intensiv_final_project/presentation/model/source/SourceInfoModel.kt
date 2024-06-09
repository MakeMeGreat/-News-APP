package com.example.aston_intensiv_final_project.presentation.model.source

import java.io.Serializable

data class SourceInfoModel(
    val id: String?,
    val name: String?,
    val description: String?,
    val url: String?,
    val category: String?,
    val language: String?,
    val country: String?,
) : Serializable