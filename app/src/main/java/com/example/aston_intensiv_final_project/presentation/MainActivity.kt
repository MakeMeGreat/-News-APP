package com.example.aston_intensiv_final_project.presentation

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.data.cache.CacheArticleDao
import com.example.aston_intensiv_final_project.data.cache.CacheSourceDao
import com.example.aston_intensiv_final_project.data.cache.DataBase
import com.example.aston_intensiv_final_project.databinding.ActivityMainBinding
import com.example.aston_intensiv_final_project.presentation.error.NoInternetFragment
import com.example.aston_intensiv_final_project.presentation.headlines.HeadlinesFragment
import com.example.aston_intensiv_final_project.presentation.saved.SavedNewsFragment
import com.example.aston_intensiv_final_project.presentation.sources.SourcesFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Job() + Dispatchers.IO).launch {
            val database = DataBase.getDatabase(this@MainActivity)
            database.cachedArticleDao().clearTable()
            database.cachedSourceDao().clearTable()
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_container, HeadlinesFragment())
                .commit()
        }
        val toolbar = binding.activityToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_arrow)

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_nav_saved -> replaceFragment(SavedNewsFragment())
                R.id.bottom_nav_sources -> replaceFragment(SourcesFragment())
                else -> replaceFragment(HeadlinesFragment())
            }
        }
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_fragment_container, fragment)
            .addToBackStack("bottomNav")//Todo
            .commit()
        return true
    }

/*    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.d("SEARCH", "Search query was: $query")
        }
    }*/
}