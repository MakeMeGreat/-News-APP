package com.example.aston_intensiv_final_project.domain.usecase

import com.example.aston_intensiv_final_project.domain.Repository
import javax.inject.Inject

class GetSearchNewsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(
        searchQuery: String
    ) = repository.getSearchNews(searchQuery = searchQuery)
}
