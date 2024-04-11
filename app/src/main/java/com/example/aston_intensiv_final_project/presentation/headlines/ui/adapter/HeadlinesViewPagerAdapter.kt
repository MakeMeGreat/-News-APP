package com.example.aston_intensiv_final_project.presentation.headlines.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aston_intensiv_final_project.presentation.headlines.ui.fragments.HeadlinesBusinessFragment
import com.example.aston_intensiv_final_project.presentation.headlines.ui.fragments.HeadlinesGeneralFragment
import com.example.aston_intensiv_final_project.presentation.headlines.ui.fragments.HeadlinesTravelingFragment

class HeadlinesViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HeadlinesGeneralFragment()
            1 -> HeadlinesBusinessFragment()
            2 -> HeadlinesTravelingFragment()
            else -> HeadlinesGeneralFragment()
        }
    }
}