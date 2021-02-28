package com.lerie_valerie.newsfeed.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
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
        if (savedInstanceState != null)
            binding.webView.restoreState(savedInstanceState)
        else {
//            wv.setWebViewClient(MyBrowser())
//            wv.getSettings().setLoadsImagesAutomatically(true)
//            wv.getSettings().setJavaScriptEnabled(true)
//            wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
//            wv.loadUrl(url)
            args.articleUrl?.let { binding.webView.loadUrl(it) }
        }

//        args.articleUrl?.let { binding.webView.loadUrl(it) }

        binding.webView.webViewClient = WebViewClient()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.webView.saveState(outState)
    }
}