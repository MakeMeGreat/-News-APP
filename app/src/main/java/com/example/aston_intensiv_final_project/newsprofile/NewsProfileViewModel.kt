package com.example.aston_intensiv_final_project.newsprofile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.aston_intensiv_final_project.headlines.data.models.ArticleDTO

class NewsProfileViewModel: ViewModel() {
    private var article: ArticleDTO? = null
    private var image: Bitmap? = null

    //Todo: how to make it better
    fun setArticle(article: ArticleDTO) {
        this.article = article
//        return if (this.article == null) {
//            this.article = article
//            true
//        } else false
    }

    fun getArticle() = article
}