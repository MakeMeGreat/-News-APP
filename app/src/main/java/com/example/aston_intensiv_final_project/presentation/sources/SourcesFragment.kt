package com.example.aston_intensiv_final_project.presentation.sources

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
import com.example.aston_intensiv_final_project.databinding.FragmentSourcesBinding
import com.example.aston_intensiv_final_project.domain.Repository
import com.example.aston_intensiv_final_project.presentation.di.App
import com.example.aston_intensiv_final_project.presentation.mapper.DomainToPresentationMapper
import com.example.aston_intensiv_final_project.presentation.model.source.SourceResponseModel
import com.example.aston_intensiv_final_project.presentation.sources.onesource.OneSourceFragment
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class SourcesFragment : MvpAppCompatFragment(), MenuProvider, SourcesView {

    //Todo: change it with DI
//    private val networkDataSource = NetworkDataSource()
//    private val dataToDomainMapper = DataToDomainMapper()
//    private val repository = RepositoryImpl(networkDataSource, dataToDomainMapper)
//    private val domainToPresentationMapper = DomainToPresentationMapper()
//    private val presenter by moxyPresenter {
//        SourcesPresenter(
//            repository,
//            domainToPresentationMapper
//        )
//    }
    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var mapper: DomainToPresentationMapper

    @InjectPresenter
    lateinit var sourcesPresenter: SourcesPresenter

    @ProvidePresenter
    fun provideSourcesPresenter(): SourcesPresenter {
        return SourcesPresenter(
            repository = repository,
            mapper = mapper
        )
    }

    private lateinit var sourcesAdapter: SourcesAdapter
    private var _binding: FragmentSourcesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSourcesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sourcesAdapter = SourcesAdapter {
            val sourceId = it.id ?: ""
            parentFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_container, OneSourceFragment.newInstance(sourceId))
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerView.adapter = sourcesAdapter
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = "Sources"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_actions, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun startLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun endLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun showSuccess(response: SourceResponseModel) {
        sourcesAdapter.submitList(response.sources)
    }

    override fun showError(message: String) {
        Log.e("Sources", "error in getting response: $message")
    }
}