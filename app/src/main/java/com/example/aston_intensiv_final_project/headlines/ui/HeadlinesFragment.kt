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
import com.example.aston_intensiv_final_project.headlines.data.models.NewsResponse
import com.example.aston_intensiv_final_project.headlines.data.repository.NewsRepository
import com.example.aston_intensiv_final_project.headlines.ui.adapter.ArticleAdapter
import com.example.aston_intensiv_final_project.headlines.ui.adapter.HeadlinesView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter

class HeadlinesFragment : MvpAppCompatFragment(), HeadlinesView {

    private val newsRepository = NewsRepository()

    private val presenter by moxyPresenter { HeadlinesPresenter(newsRepository) }

    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleAdapter = ArticleAdapter()
        binding.recyclerView.adapter = articleAdapter
/*        presenter.generalNews.observe(viewLifecycleOwner) {response ->
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
        }*/
    }

    override fun startLoading() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    override fun endLoading() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    override fun showSuccess(response: NewsResponse) {
        articleAdapter.submitList(response.articles)
    }

    override fun showError(message: String) {
        Log.e("Headlines", "error in getting response: $message")
    }
/*    fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }*/
}