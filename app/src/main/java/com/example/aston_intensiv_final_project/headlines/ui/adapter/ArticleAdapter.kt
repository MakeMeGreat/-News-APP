package com.example.aston_intensiv_final_project.headlines.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.size.Scale
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentHeadlinesItemBinding
import com.example.aston_intensiv_final_project.headlines.data.models.Article

class ArticleAdapter(
    //Todo: add listener
) : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallback) {

    class ArticleViewHolder(val binding: FragmentHeadlinesItemBinding) : ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.articleImage.load(article.urlToImage) {
                scale(Scale.FILL)
                placeholder(R.drawable.image_place_holder)
                error(R.drawable.image_not_supported_holder)
            }
            binding.sourceName.text = article.source?.name ?: ""
            binding.articleTitle.text = article.title ?: ""
            binding.sourceImage.setImageResource(R.drawable.default_source_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ArticleViewHolder(
            FragmentHeadlinesItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}