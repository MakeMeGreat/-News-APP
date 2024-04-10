package com.example.aston_intensiv_final_project.sources.ui.onesource

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentOneSourceBinding
import com.example.aston_intensiv_final_project.headlines.data.models.NewsResponse
import com.example.aston_intensiv_final_project.newsprofile.NewsProfileFragment
import com.example.aston_intensiv_final_project.sources.data.SourcesRepository
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class OneSourceFragment : MvpAppCompatFragment(), MenuProvider, OneSourceView {

    private val sourcesRepository = SourcesRepository()
    private val presenter by moxyPresenter { OneSourcePresenter(sourcesRepository) }
    private lateinit var adapter: OneSourceAdapter
    private var _binding: FragmentOneSourceBinding? = null
    private val binding get() = _binding!!
    private lateinit var sourceId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOneSourceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sourceId = requireArguments().getString(SOURCE_KEY)!!
        adapter = OneSourceAdapter { article ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_container, NewsProfileFragment.newInstance(article))
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerView.adapter = adapter
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.title = sourceId.uppercase()
        presenter.getOneSourceNews(sourceId)

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_actions, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.filter_button -> {
                //Todo
            }

            R.id.search_button -> {
                //Todo
            }

            else -> {
                parentFragmentManager.popBackStack()
            }
        }
        return true
    }

    override fun startLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun endLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun showSuccess(response: NewsResponse) {
        adapter.submitList(response.articles)
    }

    override fun showError(message: String) {
        Log.e("Sources", "error in getting response: $message")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = null
    }

    companion object {
        fun newInstance(sourceId: String): OneSourceFragment {
            return OneSourceFragment().apply {
                arguments = Bundle().also { it.putString(SOURCE_KEY, sourceId) }
            }
        }

        private const val SOURCE_KEY = "SOURCE_KEY"
    }
}