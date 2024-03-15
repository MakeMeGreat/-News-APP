package com.example.aston_intensiv_final_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

class HeadlinesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //(activity as AppCompatActivity).supportActionBar?.title = "My Title"


        return inflater.inflate(R.layout.fragment_headlines, container, false)
    }


}