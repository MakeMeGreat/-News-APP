package com.example.aston_intensiv_final_project.presentation.model.source

data class SourceResponseModel(
    val status: String,
    val sources: List<SourceInfoDTOModel>
)