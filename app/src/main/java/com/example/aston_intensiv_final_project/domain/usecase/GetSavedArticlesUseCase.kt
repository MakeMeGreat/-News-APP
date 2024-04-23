package com.example.aston_intensiv_final_project.domain.usecase

import com.example.aston_intensiv_final_project.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSavedArticlesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) { repository.getSavedArticles() }
}