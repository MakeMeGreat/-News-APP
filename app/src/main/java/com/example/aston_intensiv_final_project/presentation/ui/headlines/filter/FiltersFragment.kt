package com.example.aston_intensiv_final_project.presentation.ui.headlines.filter

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
import androidx.fragment.app.viewModels
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentFiltersBinding
import com.example.aston_intensiv_final_project.presentation.ui.headlines.filter.filtered.FilteredNewsFragment
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Locale

class FiltersFragment : Fragment(), MenuProvider {

    private val viewModel: FiltersViewModel by viewModels()

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.title = getString(R.string.filters_title)
        binding.calendarButton.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText(getString(R.string.select_date_text))
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            datePicker.addOnPositiveButtonClickListener {
                viewModel.sendEvent(FilterEvent.UpdateDateEvent(it))
            }
            datePicker.addOnNegativeButtonClickListener {
                viewModel.sendEvent(FilterEvent.UpdateDateEvent(0L))
            }
            datePicker.show(parentFragmentManager, "datePicker")
        }
        binding.filterButtonsGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            var filter = ""
            if (isChecked) {
                filter = when (checkedId) {
                    R.id.popular_button -> "popularity"
                    R.id.relevant_button -> "relevancy"
                    else -> "publishedAt"
                }

            }
            viewModel.sendEvent(FilterEvent.UpdateFilterEvent(filter))
        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, _ ->
            val language =
                when (group.checkedChipId) {
                    R.id.chip_russian -> "ru"
                    R.id.chip_german -> "de"
                    R.id.chip_arabic -> "ar"
                    R.id.chip_spanish -> "es"
                    R.id.chip_french -> "fr"
                    R.id.chip_italian -> "it"
                    R.id.chip_сhinese -> "zh"
                    else -> "en"
                }
            viewModel.sendEvent(FilterEvent.UpdateLanguageEvent(language))
//            viewModel.updateLanguage(language)
        }
        viewModel.state.observe(viewLifecycleOwner) {
            binding.calendarPickedDateTextview.text = formatMillisToDateText(it.date)
        }
//        viewModel.observe(state = ::render, sideEffect = ::handleSideEffect, lifecycleOwner = viewLifecycleOwner)
//        lifecycleScope.launch{
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                launch {
//                    viewModel.container.stateFlow.collect{render(it)}
//                }
//                launch { viewModel.container.sideEffectFlow.collect {handleSideEffect(it)} }
//            }
//        }
    }

//    private fun render(state: FilterState) {
//        binding.calendarPickedDateTextview.text = formatMillisToDateText(state.date)
//    }
//
//    private fun handleSideEffect(filterSideEffect: FilterSideEffect) {}

    private fun formatMillisToDateText(timeInMillis: Long): String {
        return if (timeInMillis != 0L) {
            val format = SimpleDateFormat("dd MMM yyyy", Locale.UK)
            format.format(timeInMillis)
        } else {
            "Choose date"
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.filters_toolbar_actions, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.done_button -> {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.activity_fragment_container, FilteredNewsFragment.newInstance(
                            viewModel.state.value!!.date,
                            viewModel.state.value!!.language,
                            viewModel.state.value!!.filter
                            //viewModel.container.stateFlow.value
                        )
                    )
                    .addToBackStack(null)
                    .commit()
            }

            else -> {
                parentFragmentManager.popBackStack()
            }
        }
        return true
    }

    override fun onPause() {
        super.onPause()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): FiltersFragment {
            return FiltersFragment()
        }
    }
}