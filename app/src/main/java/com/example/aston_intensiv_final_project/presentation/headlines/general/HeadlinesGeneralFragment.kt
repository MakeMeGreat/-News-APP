package com.example.aston_intensiv_final_project.presentation.headlines.general

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.data.RepositoryImpl
import com.example.aston_intensiv_final_project.data.mapper.DataToDomainMapper
import com.example.aston_intensiv_final_project.data.network.NetworkDataSource
import com.example.aston_intensiv_final_project.databinding.FragmentHeadlinesGeneralBinding
import com.example.aston_intensiv_final_project.presentation.headlines.HeadlinesView
import com.example.aston_intensiv_final_project.presentation.headlines.adapter.ArticleAdapter
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import com.example.aston_intensiv_final_project.presentation.newsprofile.NewsProfileFragment
import com.example.aston_intensiv_final_project.util.Constants
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class HeadlinesGeneralFragment : MvpAppCompatFragment(), HeadlinesView {

    //Todo: change it with DI
    private val networkDataSource = NetworkDataSource()
    private val dataToDomainMapper = DataToDomainMapper()
    private val repository = RepositoryImpl(networkDataSource, dataToDomainMapper)
    private val domainToPresentationMapper = DomainToPresentationMapper()
    private val presenter by moxyPresenter {
        HeadlinesGeneralPresenter(
            repository,
            domainToPresentationMapper
        )
    }

    private var _binding: FragmentHeadlinesGeneralBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlinesGeneralBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleAdapter = ArticleAdapter { article ->
//            setFragmentResult(ARTICLE_REQUEST, bundleOf(ARTICLE_KEY to article))

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_container, NewsProfileFragment.newInstance(article))
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerView.adapter = articleAdapter
        binding.recyclerView.addOnScrollListener(headlinesScrollListener)
    }

    override fun startLoading() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    override fun endLoading() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    override fun showSuccess(response: NewsResponseModel) {
        isLastPage =
            presenter.newsPage == response.totalResults / Constants.QUERY_PAGE_SIZE + 2
        articleAdapter.submitList(response.articles.toList())
    }

    override fun showError(message: String) {
        Log.e("Headlines", "error in getting response: $message")
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val headlinesScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                presenter.getNews()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    companion object {
        private const val ARTICLE_REQUEST = "ARTICLE_REQUEST"
        private const val ARTICLE_KEY = "ARTICLE_KEY"
    }
}