package com.example.aston_intensiv_final_project.presentation.newsprofile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleDtoModel

class NewsProfileViewModel : ViewModel() {
    private var article: ArticleDtoModel? = null
    private var image: Bitmap? = null

    //Todo: how to make it better
    fun setArticle(article: ArticleDtoModel) {
        this.article = article

    }

    fun getArticle() = article
}