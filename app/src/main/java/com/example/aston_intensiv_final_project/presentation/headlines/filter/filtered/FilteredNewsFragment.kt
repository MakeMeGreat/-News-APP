package com.example.aston_intensiv_final_project.presentation.headlines.filter.filtered

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentFilteredNewsBinding
import com.example.aston_intensiv_final_project.presentation.di.App
import com.example.aston_intensiv_final_project.presentation.headlines.adapter.ArticleAdapter
import com.example.aston_intensiv_final_project.presentation.newsprofile.NewsProfileFragment
import org.orbitmvi.orbit.viewmodel.observe
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.properties.Delegates

private const val DATE_KEY = "DATE_KEY"
private const val LANGUAGE_KEY = "LANGUAGE_KEY"
private const val SORT_FILTER_KEY = "SORT_FILTER_KEY"

class FilteredNewsFragment : Fragment(), MenuProvider {

    @Inject
    lateinit var filteredNewsViewModelFactory: FilteredNewsOrbitViewModel.FilteredNewsViewModelFactory

    private var _binding: FragmentFilteredNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticleAdapter
//    private val viewModel: FilteredNewsOrbitViewModel by activityViewModels()

    private var quantity = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.appComponent.inject(this)
        _binding = FragmentFilteredNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setDisplayShowHomeEnabled(true)
        adapter = ArticleAdapter { article ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_container, NewsProfileFragment.newInstance(article))
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerView.adapter = adapter
        val date = requireArguments().getLong(DATE_KEY)
        val language = requireArguments().getString(LANGUAGE_KEY)
        val sortFilter = requireArguments().getString(SORT_FILTER_KEY)
        val viewModel = filteredNewsViewModelFactory.create(getFormattedDateToQuery(date), language ?: "en", sortFilter)
        quantity = getSelectedFilterCount(date, language, sortFilter)
//        viewModel.getFilteredNews(getFormattedDateToQuery(date), language ?: "en", sortFilter)
        binding.progressBar.visibility = View.VISIBLE

        viewModel.observe(viewLifecycleOwner, state = ::render, sideEffect = ::handleSideEffect)
    }

    private fun getSelectedFilterCount(date: Long?, language: String?, sortFilter: String?): Int {
        var filterCount = 0
        if (date != null && date != 0L) filterCount++
        if (language != null && language != "") filterCount++
        if (sortFilter != null && sortFilter != "") filterCount++
        return filterCount
    }

    private fun render(state: FilteredNewsState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.INVISIBLE
        if (state.filteredNewsResponse != null && !state.isLoading) adapter.submitList(state.filteredNewsResponse.articles)
        if (state.error != null) Toast.makeText(context, "${state.error}", Toast.LENGTH_SHORT)
            .show()
        // Todo: change toast to navigation on error fragment
    }

    private fun handleSideEffect(sideEffect: FilteredNewsSideEffect) {
        when (sideEffect) {
            is FilteredNewsSideEffect.WrapperSideEffect -> {}
        }
    }

    private fun getFormattedDateToQuery(dateInMillis: Long): String? {
        if (dateInMillis == 0L) return null
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        return format.format(dateInMillis)
    }

    override fun onPause() {
        super.onPause()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.filtered_news_toolbar, menu)
        val menuItem = menu.findItem(R.id.filtered_news_button)
        val actionMenuView = menuItem.actionView
        val textView = actionMenuView?.findViewById<TextView>(R.id.cart_badge_text_view)
        textView?.text = quantity.toString()
        actionMenuView?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        parentFragmentManager.popBackStack()
        return true
    }

    companion object {
        fun newInstance(
            data: Long,
            language: String,
            sortFilter: String
        ): FilteredNewsFragment {
            return FilteredNewsFragment().apply {
                arguments = Bundle().also {
                    it.putLong(DATE_KEY, data)
                    it.putString(LANGUAGE_KEY, language)
                    it.putString(SORT_FILTER_KEY, sortFilter)
                }
            }
        }
    }
}