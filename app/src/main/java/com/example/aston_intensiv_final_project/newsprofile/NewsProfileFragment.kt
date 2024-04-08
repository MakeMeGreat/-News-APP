package com.example.aston_intensiv_final_project.newsprofile

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
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.aston_intensiv_final_project.R
import com.example.aston_intensiv_final_project.databinding.FragmentNewsProfileBinding
import com.example.aston_intensiv_final_project.headlines.data.models.Article
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class NewsProfileFragment : Fragment() {

    private val viewModel: NewsProfileViewModel by activityViewModels()

    private var _binding: FragmentNewsProfileBinding? = null
    private val binding get() = _binding!!

    private var article: Article? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()
        _binding = FragmentNewsProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(ARTICLE_KEY, Article::class.java)
        } else requireArguments().getSerializable(ARTICLE_KEY) as Article
        viewModel.setArticle(article!!)
        bindArticle(viewModel.getArticle()!!)
        bindToolbar()
    }

    private fun bindArticle(article: Article) {
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
                makeContentTextSpannable(article)
            } else showContentPlaceHolder()
            binding.date.text = formatDate(article.publishedAt ?: "")
        }
    }

    private fun makeContentTextSpannable(article: Article) {
        val spannableString = SpannableString(article.content)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(intent)
            }
        }
        spannableString.setSpan(clickableSpan, determineFirstCharOfLastSentence(article.content!!), article.content.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
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
                //Todo: add it to saved articles database
                Toast.makeText(context, "it will saved", LENGTH_SHORT).show()
                true
            } else false
        }
    }

    private fun determineFirstCharOfLastSentence(str: String): Int {
        var position = 0
        str.forEachIndexed{ index, element ->
            if( element == '\r') position = index
        }
        return position
    }

    private fun showContentPlaceHolder() {
        binding.contentPlaceholderImage.visibility = View.VISIBLE
        binding.contentPlaceholderText.visibility = View.VISIBLE
    }

    private fun formatDate(date: String): String {
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
        private const val ARTICLE_REQUEST = "ARTICLE_REQUEST"
        private const val ARTICLE_KEY = "ARTICLE_KEY"

        fun newInstance(article: Article): NewsProfileFragment {
            return NewsProfileFragment().apply {
                arguments = Bundle().also { it.putSerializable(ARTICLE_KEY, article) }
            }
        }
    }
}