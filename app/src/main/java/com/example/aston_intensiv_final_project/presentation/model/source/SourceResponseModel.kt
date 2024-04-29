package com.example.aston_intensiv_final_project.presentation.model.source

import java.io.Serializable

data class SourceResponseModel(
    val status: String,
    val sources: List<SourceInfoModel>
) : Serializable