package com.example.aston_intensiv_final_project.presentation.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentSavedNewsBinding
import com.example.aston_intensiv_final_project.di.App
import com.example.aston_intensiv_final_project.presentation.ui.headlines.adapter.ArticleAdapter
import com.example.aston_intensiv_final_project.presentation.ui.newsprofile.NewsProfileFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedNewsFragment : Fragment(), MenuProvider, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @Inject
    lateinit var viewModeLFactory: SavedViewModel.SavedViewModelFactory
    private lateinit var viewModel: SavedViewModel

    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.appComponent.inject(this)
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModeLFactory).get(SavedViewModel::class.java)
        val adapter = ArticleAdapter { article ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_container, NewsProfileFragment.newInstance(article))
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.savedArticlesStateFlow.collect {
                    adapter.submitList(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoadingStateFlow.collect {
                    if (!it) swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        activity?.addMenuProvider(this, viewLifecycleOwner)
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = getString(R.string.saved_title)
        swipeRefreshLayout = binding.swipeContainer
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        viewModel.refresh()
        viewModel.getSavedArticles()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_actions, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.search_button -> {}
            R.id.filter_button -> {}
        }
        return true
    }
}