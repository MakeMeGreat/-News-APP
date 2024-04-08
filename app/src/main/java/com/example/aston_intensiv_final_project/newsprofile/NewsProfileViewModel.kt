package com.example.aston_intensiv_final_project.newsprofile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.aston_intensiv_final_project.headlines.data.models.Article
import kotlinx.coroutines.launch

class NewsProfileViewModel: ViewModel() {
    private var article: Article? = null
    private var image: Bitmap? = null

    //Todo: how to make it better
    fun setArticle(article: Article) {
        this.article = article
//        return if (this.article == null) {
//            this.article = article
//            true
//        } else false
    }

    fun getArticle() = article

//    private fun getImage(article: Article): Bitmap {
//        viewModelScope.launch {
//            val loader = ImageLoader()
//            val request = ImageRequest.Builder(this)
//                .data("https://images.dog.ceo/breeds/saluki/n02091831_3400.jpg")
//                .allowHardware(false) // Disable hardware bitmaps.
//                .build()
//
//            val result = (loader.execute(request) as SuccessResult).drawable
//            val bitmap = (result as BitmapDrawable).bitmap
//        }
//    }
}