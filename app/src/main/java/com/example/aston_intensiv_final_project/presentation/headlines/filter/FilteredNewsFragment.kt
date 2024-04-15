package com.example.aston_intensiv_final_project.presentation.headlines.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentFilteredNewsBinding
import com.example.aston_intensiv_final_project.presentation.headlines.adapter.ArticleAdapter
import com.example.aston_intensiv_final_project.presentation.newsprofile.NewsProfileFragment

private const val DATE_KEY = "DATE_KEY"
private const val LANGUAGE_KEY = "LANGUAGE_KEY"
private const val SORT_FILTER_KEY = "SORT_FILTER_KEY"

class FilteredNewsFragment : Fragment() {

    private var _binding: FragmentFilteredNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticleAdapter
    private val viewModel : FilteredNewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteredNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ArticleAdapter { article ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_container, NewsProfileFragment.newInstance(article))
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerView.adapter = adapter
        val date = requireArguments().getString(DATE_KEY)
        val language = requireArguments().getString(LANGUAGE_KEY)
        val sortFilter = requireArguments().getString(SORT_FILTER_KEY)
        viewModel.getFilteredNews(date, language ?: "en", sortFilter)
        binding.progressBar.visibility = View.VISIBLE
        viewModel.filteredNews.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.INVISIBLE
            adapter.submitList(it.articles)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(
            data: String?,
            language: String?,
            sortFilter: String?
        ): FilteredNewsFragment {
            return FilteredNewsFragment().apply {
                arguments = Bundle().also {
                    it.putString(DATE_KEY, data)
                    it.putString(LANGUAGE_KEY, language)
                    it.putString(SORT_FILTER_KEY, sortFilter)
                }
            }
        }
    }
}