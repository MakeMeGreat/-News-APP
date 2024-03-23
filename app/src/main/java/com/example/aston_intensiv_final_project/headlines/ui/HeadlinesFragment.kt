package com.example.aston_intensiv_final_project.headlines.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentHeadlinesBinding
import com.example.aston_intensiv_final_project.headlines.data.repository.NewsRepository
import com.example.aston_intensiv_final_project.headlines.ui.adapter.ArticleAdapter

class HeadlinesFragment : Fragment() {

    private val newsRepository = NewsRepository()
    private val viewModel: HeadlinesViewModel by viewModels{ HeadlinesViewModelFactory(newsRepository) }

    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleAdapter = ArticleAdapter()
        binding.recyclerView.adapter = articleAdapter
        viewModel.generalNews.observe(viewLifecycleOwner) {response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {  newsResponse ->
                        articleAdapter.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("Headlines", "error in getting response: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
}