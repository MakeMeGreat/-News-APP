package com.example.aston_intensiv_final_project.presentation.ui.sources.onesource

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentOneSourceBinding
import com.example.aston_intensiv_final_project.domain.usecase.GetOneSourceNewsUseCase
import com.example.aston_intensiv_final_project.di.App
import com.example.aston_intensiv_final_project.presentation.ui.headlines.adapter.ArticleAdapter
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import com.example.aston_intensiv_final_project.presentation.ui.newsprofile.NewsProfileFragment
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class OneSourceFragment : MvpAppCompatFragment(), MenuProvider, OneSourceView {

    @Inject
    lateinit var getOneSourceNewsUseCase: GetOneSourceNewsUseCase

    @Inject
    lateinit var mapper: DomainToPresentationMapper

    @InjectPresenter
    lateinit var oneSourcePresenter: OneSourcePresenter

    @ProvidePresenter
    fun provideOneSourcePresenter(): OneSourcePresenter {
        return OneSourcePresenter(
            getOneSourceNewsUseCase = getOneSourceNewsUseCase,
            mapper = mapper
        )
    }

    private lateinit var adapter: ArticleAdapter
    private var _binding: FragmentOneSourceBinding? = null
    private val binding get() = _binding!!
    private lateinit var sourceId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneSourceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sourceId = requireArguments().getString(SOURCE_KEY)!!
        adapter = ArticleAdapter { article ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_container, NewsProfileFragment.newInstance(article))
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerView.adapter = adapter
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.title = sourceId.uppercase()
        oneSourcePresenter.getOneSourceNews(sourceId)

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        parentFragmentManager.popBackStack()
        return true
    }

    override fun startLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun endLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun showSuccess(response: NewsResponseModel) {
        adapter.submitList(response.articles)
    }

    override fun showError(message: String) {
        Log.e("Sources", "error in getting response: $message")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = null
    }

    companion object {
        fun newInstance(sourceId: String): OneSourceFragment {
            return OneSourceFragment().apply {
                arguments = Bundle().also { it.putString(SOURCE_KEY, sourceId) }
            }
        }

        private const val SOURCE_KEY = "SOURCE_KEY"
    }
}