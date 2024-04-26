package com.example.aston_intensiv_final_project.presentation.sources

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResultListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentSourcesBinding
import com.example.aston_intensiv_final_project.domain.usecase.GetSourcesUseCase
import com.example.aston_intensiv_final_project.presentation.di.App
import com.example.aston_intensiv_final_project.presentation.error.NoInternetFragment
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.source.SourceResponseModel
import com.example.aston_intensiv_final_project.presentation.sources.filter.SourcesFiltersFragment
import com.example.aston_intensiv_final_project.presentation.sources.onesource.OneSourceFragment
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

private const val REQUEST_KEY = "REQUEST_KEY"
private const val CATEGORY_KEY = "CATEGORY_KEY"
private const val LANGUAGE_KEY = "LANGUAGE_KEY"

class SourcesFragment : MvpAppCompatFragment(), MenuProvider, SourcesView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @Inject
    lateinit var getSourcesUseCase: GetSourcesUseCase

    @Inject
    lateinit var mapper: DomainToPresentationMapper

    @InjectPresenter
    lateinit var sourcesPresenter: SourcesPresenter

    @ProvidePresenter
    fun provideSourcesPresenter(): SourcesPresenter {
        return SourcesPresenter(
            getSourcesUseCase = getSourcesUseCase,
            mapper = mapper
        )
    }

    private lateinit var sourcesAdapter: SourcesAdapter
    private var _binding: FragmentSourcesBinding? = null
    private val binding get() = _binding!!

    private var enabledFiltersCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSourcesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sourcesAdapter = SourcesAdapter {source ->
//            val sourceId = it.id ?: ""
            parentFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_container, OneSourceFragment.newInstance(source.id ?: ""))
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerView.adapter = sourcesAdapter
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = "Sources"
        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val languageFilter = bundle.getString(LANGUAGE_KEY)
            val categoryFilter = bundle.getString(CATEGORY_KEY)
            enabledFiltersCount = getEnabledFiltersCount(languageFilter, categoryFilter)

            sourcesPresenter.getSources(
                language = languageFilter ?: "",
                category = categoryFilter ?: ""
            )
        }
        swipeRefreshLayout = binding.swipeContainer
        swipeRefreshLayout.setOnRefreshListener(this)
        activity?.supportFragmentManager?.setFragmentResultListener("update_request_key", viewLifecycleOwner) { key, bundle ->
            val result = bundle.getString("update_bundle_key")
//            Toast.makeText(requireContext(), "$result", Toast.LENGTH_SHORT).show()
            sourcesPresenter.getSources()
        }
    }

    private fun getEnabledFiltersCount(language: String?, category: String?): Int {
        var count = 0
        if (language != null && language != "") count++
        if (category != null && category != "") count++
        return count
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.sources_actions, menu)
        val menuSearchItem = menu.findItem(R.id.search_button)
        val searchView = menuSearchItem.actionView as SearchView?
        searchView?.queryHint = "category"
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                sourcesPresenter.getSources(category = query?.replace(" ", "") ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        val menuFilterItem = menu.findItem(R.id.filtered_sources_button)
        val actionView = menuFilterItem.actionView
        val textView = actionView?.findViewById<TextView>(R.id.filter_badge_text_view)
        textView?.text = enabledFiltersCount.toString()
        textView?.visibility = if (enabledFiltersCount == 0) View.GONE else View.VISIBLE
        actionView?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_container, SourcesFiltersFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.search_button -> {

            }

            else -> parentFragmentManager.popBackStack()
        }

        return true
    }

    override fun onRefresh() {
        sourcesPresenter.refreshSource()
        enabledFiltersCount = 0
        requireActivity().invalidateOptionsMenu()
    }

    override fun startLoading() {
        binding.progressBar.visibility = View.VISIBLE
//        binding.recyclerView.visibility = View.INVISIBLE
    }

    override fun endLoading() {
        binding.progressBar.visibility = View.INVISIBLE
//        binding.recyclerView.visibility = View.VISIBLE
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showSuccess(response: SourceResponseModel) {
        sourcesAdapter.submitList(response.sources)
    }

    override fun showError(message: String) {
        Log.e("Sources", "error in getting response: $message")
    }

    override fun showNoInternet() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.activity_fragment_container, NoInternetFragment())
            .addToBackStack(null)
            .commit()
    }
}