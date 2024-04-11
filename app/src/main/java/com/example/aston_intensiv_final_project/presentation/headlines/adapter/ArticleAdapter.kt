package com.example.aston_intensiv_final_project.presentation.headlines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentArticlesItemBinding
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleDTOModel

class ArticleAdapter(
    private val onArticleClicked: (ArticleDTOModel) -> Unit,
) : ListAdapter<ArticleDTOModel, ArticleAdapter.ArticleViewHolder>(DiffCallback) {

    inner class ArticleViewHolder(val binding: FragmentArticlesItemBinding) :
        ViewHolder(binding.root) {
        fun bind(article: ArticleDTOModel) {
            binding.apply {
                articleImage.load(article.urlToImage) {
                    placeholder(R.drawable.image_place_holder)
                    error(R.drawable.image_not_supported_holder)
                    build()
                }
                sourceName.text = article.source?.name ?: ""
                articleTitle.text = article.title ?: ""
                sourceImage.setImageResource(R.drawable.cnn_source_image)
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

    companion object DiffCallback : DiffUtil.ItemCallback<ArticleDTOModel>() {
        override fun areItemsTheSame(oldItem: ArticleDTOModel, newItem: ArticleDTOModel): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: ArticleDTOModel,
            newItem: ArticleDTOModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}