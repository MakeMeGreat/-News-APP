package com.example.aston_intensiv_final_project.domain.model.source


data class SourceResponseDomainModel(
    val status: String,
    val sources: List<SourceInfoDTODomainModel>
)
