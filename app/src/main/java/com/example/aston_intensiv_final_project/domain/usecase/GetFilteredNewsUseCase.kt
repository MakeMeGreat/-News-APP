package com.example.aston_intensiv_final_project.domain.usecase

import com.example.aston_intensiv_final_project.domain.Repository
import javax.inject.Inject

class GetFilteredNewsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(
        from: String?,
        language: String?,
        sortBy: String?,
    ) = repository.getFilteredNews(from, language, sortBy)
}