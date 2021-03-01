package com.lerie_valerie.newsfeed.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.lerie_valerie.newsfeed.databinding.FragmentNewsDetailBinding


class NewsDetailFragment: Fragment()  {
    private val args: NewsDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentNewsDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsDetailBinding.inflate(inflater, container, false)
            .apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.webView.webViewClient = WebViewClientEx(binding.progressBar)
        binding.webView.webChromeClient = WebChromeClientEx(binding.progressBar)

        if (savedInstanceState != null)
            binding.webView.restoreState(savedInstanceState)
        else {
            args.articleUrl?.let { binding.webView.loadUrl(it) }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.webView.saveState(outState)
    }

    class WebChromeClientEx(private val progressBar: ProgressBar) : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            progressBar.visibility = View.VISIBLE
            progressBar.progress = newProgress
        }
    }

    class WebViewClientEx(private val progressBar: ProgressBar) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }
    }
}