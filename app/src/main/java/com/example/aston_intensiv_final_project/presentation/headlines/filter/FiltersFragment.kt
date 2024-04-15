package com.example.aston_intensiv_final_project.presentation.headlines.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentFiltersBinding
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
        toolbar?.title = "Filters"
        if (parentFragmentManager.backStackEntryCount > 0) {
            toolbar?.setDisplayHomeAsUpEnabled(true)
            toolbar?.setDisplayShowHomeEnabled(true)
        }
        binding.calendarButton.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            datePicker.addOnPositiveButtonClickListener {
                viewModel.dateLiveData.value = it
            }
            datePicker.addOnNegativeButtonClickListener {
                viewModel.dateLiveData.value = 0L
            }
            datePicker.show(parentFragmentManager, "datePicker")
        }
        binding.buttonsGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                viewModel.sortByLiveData.value =
                    when (checkedId) {
                        R.id.popular_button -> "popularity"
                        R.id.relevant_button -> "relevancy"
                        else -> "publishedAt"
                    }
            }
        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, _ ->
            viewModel.languageLiveData.value =
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
        }
        viewModel.languageLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, "$it language selected", LENGTH_SHORT).show()

        }
        viewModel.sortByLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, "$it sort filter selected", LENGTH_SHORT).show()
        }

        viewModel.dateLiveData.observe(viewLifecycleOwner) {
            binding.calendarPickedDateTextview.text = formatMillisToDateText(it)
        }
    }

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
                            viewModel.getFormattedDateToQuery(),
                            viewModel.languageLiveData.value,
                            viewModel.sortByLiveData.value
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