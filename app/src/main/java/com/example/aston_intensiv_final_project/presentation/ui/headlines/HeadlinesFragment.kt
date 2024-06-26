package com.example.aston_intensiv_final_project.presentation.ui.headlines

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
import androidx.viewpager2.widget.ViewPager2
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentHeadlinesBinding
import com.example.aston_intensiv_final_project.presentation.ui.headlines.adapter.HeadlinesViewPagerAdapter
import com.example.aston_intensiv_final_project.presentation.ui.headlines.filter.FiltersFragment
import com.example.aston_intensiv_final_project.presentation.ui.headlines.search.SearchFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab

class HeadlinesFragment : Fragment(), MenuProvider {

    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = getString(R.string.app_name)
        toolbar?.setDisplayHomeAsUpEnabled(false)

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        val viewPagerAdapter = HeadlinesViewPagerAdapter(this)
        viewPager.isUserInputEnabled = false
        viewPager.adapter = viewPagerAdapter


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: Tab?) {}
            override fun onTabReselected(tab: Tab?) {}
        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
            }
        })
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_actions, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.filter_button ->
                parentFragmentManager.beginTransaction()
                    .replace(R.id.activity_fragment_container, FiltersFragment.newInstance())
                    .addToBackStack(null)
                    .commit()

            R.id.search_button -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.activity_fragment_container, SearchFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
        return true
    }

}