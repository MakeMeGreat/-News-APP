package com.example.aston_intensiv_final_project.sources.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentSourcesBinding
import com.example.aston_intensiv_final_project.sources.data.SourcesRepository
import com.example.aston_intensiv_final_project.sources.data.models.SourcesResponse
import moxy.MvpAppCompatFragment
import moxy.MvpFragment
import moxy.ktx.moxyPresenter

class SourcesFragment : MvpAppCompatFragment(), MenuProvider, SourcesView {

    private val sourcesRepository = SourcesRepository()
    private val presenter by moxyPresenter { SourcesPresenter(sourcesRepository) }
    private lateinit var sourcesAdapter: SourcesAdapter
    private var _binding: FragmentSourcesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSourcesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sourcesAdapter = SourcesAdapter()
        binding.recyclerView.adapter = sourcesAdapter
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = "Sources"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_actions, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun startLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun endLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun showSuccess(response: SourcesResponse) {
        sourcesAdapter.submitList(response.sources)
    }

    override fun showError(message: String) {
        Log.e("Sources", "error in getting response: $message")
    }
}