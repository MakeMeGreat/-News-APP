package com.example.aston_intensiv_final_project.presentation.newsprofile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleDTOModel

class NewsProfileViewModel : ViewModel() {
    private var article: ArticleDTOModel? = null
    private var image: Bitmap? = null

    //Todo: how to make it better
    fun setArticle(article: ArticleDTOModel) {
        this.article = article
//        return if (this.article == null) {
//            this.article = article
//            true
//        } else false
    }

    fun getArticle() = article
}