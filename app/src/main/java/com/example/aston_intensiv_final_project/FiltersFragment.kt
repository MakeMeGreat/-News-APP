package com.example.aston_intensiv_final_project

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
import com.example.aston_intensiv_final_project.databinding.FragmentFiltersBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Locale

class FiltersFragment : Fragment(), MenuProvider {

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
        var date = 0L
        binding.calendarButton.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            datePicker.addOnPositiveButtonClickListener {
                val format = SimpleDateFormat("dd MMM yyyy", Locale.UK)
                date = it
                binding.calendarPickedDateTextview.text = format.format(date)
            }
            datePicker.addOnNegativeButtonClickListener {
                date = 0L
                binding.calendarPickedDateTextview.text = "Choose date"
            }
            datePicker.show(parentFragmentManager, "datePicker")
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.filters_toolbar_actions, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.done_button -> {
                Toast.makeText(context," done pressed", LENGTH_SHORT).show()
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