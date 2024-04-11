package com.example.aston_intensiv_final_project.presentation

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.ActivityMainBinding
import com.example.aston_intensiv_final_project.presentation.headlines.HeadlinesFragment
import com.example.aston_intensiv_final_project.presentation.newsprofile.NewsProfileFragment
import com.example.aston_intensiv_final_project.presentation.sources.SourcesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        handleIntent(intent)
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
                R.id.bottom_nav_saved -> replaceFragment(NewsProfileFragment())
//                 {                    TODO()}
                R.id.bottom_nav_sources -> replaceFragment(SourcesFragment())
                else -> replaceFragment(HeadlinesFragment())
            }
        }
    }


    /*  override fun onCreateOptionsMenu(menu: Menu): Boolean {
          menuInflater.inflate(R.menu.toolbar_actions, menu)
          val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
          val searchView = menu.findItem(R.id.search_button).actionView as SearchView
          val component = ComponentName(this, MainActivity::class.java)
          val searchableInfo = searchManager.getSearchableInfo(component)
          searchView.setSearchableInfo(searchableInfo)
          menu.findItem(R.id.filter_button).setOnMenuItemClickListener {
              supportFragmentManager.beginTransaction()
                  .replace(R.id.activity_fragment_container, FiltersFragment())
                  .addToBackStack(null)
                  .commit()
              true
          }
          return true
      }*/
    private fun replaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_fragment_container, fragment)
            .commit()
        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.d("SEARCH", "Search query was: $query")
        }
    }
}