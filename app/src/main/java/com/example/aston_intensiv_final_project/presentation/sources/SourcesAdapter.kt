package com.example.aston_intensiv_final_project.presentation.sources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentSourcesItemBinding
import com.example.aston_intensiv_final_project.presentation.model.source.SourceInfoDtoModel
import java.util.Locale

class SourcesAdapter(
    private val onSourceClicked: (SourceInfoDtoModel) -> Unit,
) : ListAdapter<SourceInfoDtoModel, SourcesAdapter.SourcesViewHolder>(DiffCallback) {

    inner class SourcesViewHolder(val binding: FragmentSourcesItemBinding) :
        ViewHolder(binding.root) {
        fun bind(source: SourceInfoDtoModel) {
            binding.apply {
                sourceName.text = source.name
                sourceImage.setImageResource(determineSourceImage(source))
                sourceDescription.text =
                    "${capitalize(source.category ?: "")} | ${source.country?.uppercase()}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SourcesViewHolder(
            FragmentSourcesItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SourcesViewHolder, position: Int) {
        val source = getItem(position)
        holder.itemView.setOnClickListener {
            onSourceClicked(source)
        }
        holder.bind(source)
    }

    private fun capitalize(str: String): String {
        return str.replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SourceInfoDtoModel>() {
        override fun areItemsTheSame(
            oldItem: SourceInfoDtoModel,
            newItem: SourceInfoDtoModel
        ): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: SourceInfoDtoModel,
            newItem: SourceInfoDtoModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun determineSourceImage(source: SourceInfoDtoModel): Int {
        return if (source.name == null) {
            R.drawable.cnn_source_image
        } else if (source.name.contains("nbc", true)) {
            R.drawable.nbc_source_image
        } else if (source.name.contains("bbc", true)) {
            R.drawable.bbc_source_image
        } else if (source.name.contains("bloomberg", true)) {
            R.drawable.bloomberg_source_image
        } else if (source.name.contains("fox", true)) {
            R.drawable.fox_news_source_image
        } else R.drawable.cnn_source_image
    }
}

