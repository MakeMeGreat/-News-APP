package com.example.aston_intensiv_final_project.presentation.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.aston_intensiv_final_project.databinding.FragmentNoInternetBinding

private const val UPDATE_REQUEST_KEY = "UPDATE_REQUEST_KEY"
private const val UPDATE_BUNDLE_KEY = "UPDATE_BUNDLE_KEY"

class NoInternetFragment : Fragment() {

    private var _binding: FragmentNoInternetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoInternetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.refreshButton.setOnClickListener {
            activity?.supportFragmentManager?.setFragmentResult(
                UPDATE_REQUEST_KEY,
                bundleOf(UPDATE_BUNDLE_KEY to "update")
            )
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}