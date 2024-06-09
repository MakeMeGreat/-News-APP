package com.example.aston_intensiv_final_project.domain.usecase

import com.example.aston_intensiv_final_project.domain.Repository
import com.example.aston_intensiv_final_project.domain.model.news.ArticleDtoDomainModel
import javax.inject.Inject

class SaveOrDeleteArticleUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(
        articleDtoDomainModel: ArticleDtoDomainModel
    ) = repository.saveOrDeleteArticle(articleDtoDomainModel)
}