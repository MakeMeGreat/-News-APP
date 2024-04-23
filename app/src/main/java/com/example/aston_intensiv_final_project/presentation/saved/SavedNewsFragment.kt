package com.example.aston_intensiv_final_project.presentation.saved

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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentSavedNewsBinding
import com.example.aston_intensiv_final_project.presentation.di.App
import com.example.aston_intensiv_final_project.presentation.newsprofile.NewsProfileFragment
import com.example.aston_intensiv_final_project.presentation.sources.onesource.OneSourceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavedNewsFragment : Fragment(), MenuProvider {

    @Inject
    lateinit var viewModel: SavedViewModel

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
        val adapter = OneSourceAdapter { article ->
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

        activity?.addMenuProvider(this, viewLifecycleOwner)
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = "Saved"
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_actions, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}