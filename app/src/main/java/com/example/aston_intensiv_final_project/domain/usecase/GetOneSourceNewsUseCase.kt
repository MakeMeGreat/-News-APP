package com.example.aston_intensiv_final_project.domain.usecase

import com.example.aston_intensiv_final_project.domain.Repository
import javax.inject.Inject

class GetOneSourceNewsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(
        sourceId: String,
    ) = repository.getOneSourceNews(sourceId = sourceId)
}