package com.example.aston_intensiv_final_project.presentation.ui.headlines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentArticlesItemBinding
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleModel

class ArticleAdapter(
    private val onArticleClicked: (ArticleModel) -> Unit,
) : ListAdapter<ArticleModel, ArticleAdapter.ArticleViewHolder>(DiffCallback) {

    inner class ArticleViewHolder(val binding: FragmentArticlesItemBinding) :
        ViewHolder(binding.root) {
        fun bind(article: ArticleModel) {
            binding.apply {
                articleImage.load(article.urlToImage) {
                    placeholder(R.drawable.image_place_holder)
                    error(R.drawable.image_not_supported_holder)
                    build()
                }
                sourceName.text = article.source?.name ?: ""
                articleTitle.text = article.title ?: ""
                sourceImage.setImageResource(determineSourceImage(article.source?.name))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ArticleViewHolder(
            FragmentArticlesItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.itemView.setOnClickListener {
            onArticleClicked(article)
        }
        holder.bind(article)
    }

    fun determineSourceImage(sourceName: String?): Int {
        return if (sourceName == null) {
            R.drawable.cnn_source_image
        } else if (sourceName.contains("nbc", true)) {
            R.drawable.nbc_source_image
        } else if (sourceName.contains("bbc", true)) {
            R.drawable.bbc_source_image
        } else if (sourceName.contains("bloomberg", true)) {
            R.drawable.bloomberg_source_image
        } else if (sourceName.contains("fox", true)) {
            R.drawable.fox_news_source_image
        } else R.drawable.cnn_source_image
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ArticleModel>() {
        override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: ArticleModel,
            newItem: ArticleModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}