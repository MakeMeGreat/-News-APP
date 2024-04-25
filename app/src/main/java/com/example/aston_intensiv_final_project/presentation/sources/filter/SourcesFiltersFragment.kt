package com.example.aston_intensiv_final_project.presentation.sources.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentSourcesFiltersBinding
import org.orbitmvi.orbit.viewmodel.observe

private const val REQUEST_KEY = "REQUEST_KEY"
private const val CATEGORY_KEY = "CATEGORY_KEY"
private const val LANGUAGE_KEY = "LANGUAGE_KEY"

class SourcesFiltersFragment : Fragment(), MenuProvider {

    private val viewModel: SourcesFilterViewModel by activityViewModels()

    private var _binding: FragmentSourcesFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSourcesFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.title = "Filters"
        viewModel.observe(viewLifecycleOwner, state = ::render, sideEffect = ::handleSideEffect)
        binding.categoryChipGroup.setOnCheckedStateChangeListener { group, _ ->
            viewModel.sendEvent(SourceFilterEvent.UpdateCategoryEvent(group.checkedChipId))
        }
        binding.languageChipGroup.setOnCheckedStateChangeListener { group, _ ->
            viewModel.sendEvent(SourceFilterEvent.UpdateLanguageEvent(group.checkedChipId))
        }
    }

    private fun render(state: SourcesFilterState) {
        binding.languageChipGroup.check(state.languageFilter)
        binding.categoryChipGroup.check(state.categoryFilter)
    }

    private fun handleSideEffect(sideEffect: SourcesFilterSideEffect) {}


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.filters_toolbar_actions, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.done_button -> {
                val state = viewModel.container.stateFlow.value
                setFragmentResult(
                    requestKey = REQUEST_KEY,
                    bundleOf(
                        CATEGORY_KEY to determineWhichCategory(state.categoryFilter),
                        LANGUAGE_KEY to determineWhichLanguage(state.languageFilter)
                    )
                )
                parentFragmentManager.popBackStack()
            }

            else -> {
                parentFragmentManager.popBackStack()
            }
        }
        return true
    }

    private fun determineWhichCategory(categoryChipId: Int): String {
        return when (categoryChipId) {
            R.id.category_chip_business -> "business"
            R.id.category_chip_general -> "general"
            R.id.category_chip_entertainment -> "entertainment"
            R.id.category_chip_health -> "health"
            R.id.category_chip_science -> "science"
            R.id.category_chip_sports -> "sports"
            R.id.category_chip_technology -> "technology"
            else -> ""
        }
    }

    private fun determineWhichLanguage(languageChipId: Int): String {
        return when (languageChipId) {
            R.id.language_chip_english -> "en"
            R.id.language_chip_russian -> "ru"
            R.id.language_chip_german -> "de"
            R.id.language_chip_arabic -> "ar"
            R.id.language_chip_spanish -> "es"
            R.id.language_chip_french -> "fr"
            R.id.language_chip_italian -> "it"
            R.id.language_chip_сhinese -> "zh"
            else -> ""
        }
    }

    override fun onPause() {
        super.onPause()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}