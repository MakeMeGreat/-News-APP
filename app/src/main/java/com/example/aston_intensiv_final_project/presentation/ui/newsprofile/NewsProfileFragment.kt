package com.example.aston_intensiv_final_project.presentation.ui.newsprofile

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentNewsProfileBinding
import com.example.aston_intensiv_final_project.di.App
import com.example.aston_intensiv_final_project.presentation.model.news.ArticleModel
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class NewsProfileFragment : Fragment() {

    @Inject
    lateinit var newsProfileViewModelFactory: NewsProfileViewModel.NewsProfileViewModelFactory

    private lateinit var viewModel: NewsProfileViewModel

    private var _binding: FragmentNewsProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.appComponent.inject(this)
        _binding = FragmentNewsProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(ARTICLE_KEY, ArticleModel::class.java)
        } else requireArguments().getSerializable(ARTICLE_KEY) as ArticleModel
        viewModel = newsProfileViewModelFactory.create(article!!)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articleFlow.collect {
                    bindArticle(it)
                }
            }
        }

        bindToolbar()

    }

    private fun bindArticle(article: ArticleModel) {
        binding.apply {
            image.load(article.urlToImage) {
                crossfade(true)
                crossfade(300)
                error(R.drawable.image_not_supported_holder)
                build()
            }
            collapsingToolbar.title = article.title
            title.text = article.title
            source.text = article.source?.name
            if (article.content != null) {
                makeLastSentenceOfTextSpannable(article)
            } else showContentPlaceHolder()
            binding.date.text = getFormattedDate(article.publishedAt ?: "")
        }
    }

    private fun makeLastSentenceOfTextSpannable(article: ArticleModel) {
        val spannableString = SpannableString(article.content)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(intent)
            }
        }
        spannableString.setSpan(
            clickableSpan,
            determineFirstCharOfLastSentence(article.content!!),
            article.content.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        binding.content.text = spannableString
        binding.content.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun bindToolbar() {
        val toolbar = binding.toolbar
        toolbar.inflateMenu(R.menu.artilce_profile_actions)
        toolbar.setNavigationIcon(R.drawable.back_arrow)
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.bookmark_button) {
                viewModel.insertOrDelete()
                true
            } else false
        }
    }

    private fun determineFirstCharOfLastSentence(str: String): Int {
        var position = 0
        str.forEachIndexed { index, element ->
            if (element == '\r') position = index
        }
        return position
    }

    private fun showContentPlaceHolder() {
        binding.contentPlaceholderImage.visibility = View.VISIBLE
        binding.contentPlaceholderText.visibility = View.VISIBLE
    }

    private fun getFormattedDate(date: String): String {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("MMM dd, yyyy | hh:mm a", Locale.US)
        return try {
            outputFormat.format(isoFormat.parse(date)!!)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    companion object {
        private const val ARTICLE_KEY = "ARTICLE_KEY"

        fun newInstance(article: ArticleModel): NewsProfileFragment {
            return NewsProfileFragment().apply {
                arguments = Bundle().also { it.putSerializable(ARTICLE_KEY, article) }
            }
        }
    }
}