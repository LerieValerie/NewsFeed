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




//        setProgressBarVisibility(View.VISIBLE)
//        webView.setWebViewClient(object : WebViewClient() {
//            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
//                super.onPageStarted(view, url, favicon)
//                setProgressBarVisibility(View.VISIBLE)
//            }
//
//            override fun onPageFinished(view: WebView, url: String) {
//                super.onPageFinished(view, url)
//                setProgressBarVisibility(View.GONE)
//            }
//
//            override fun onReceivedError(
//                view: WebView,
//                request: WebResourceRequest,
//                error: WebResourceError
//            ) {
//                super.onReceivedError(view, request, error)
//                Toast.makeText(activity, "Cannot load page", Toast.LENGTH_SHORT).show()
//                setProgressBarVisibility(View.GONE)
//            }
//        })

        if (savedInstanceState != null)
            binding.webView.restoreState(savedInstanceState)
        else {
//            wv.setWebViewClient(MyBrowser())
//            wv.getSettings().setLoadsImagesAutomatically(true)
//            wv.getSettings().setJavaScriptEnabled(true)
//            wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
//            wv.loadUrl(url)
//            binding.progressBar.visibility = View.VISIBLE
            args.articleUrl?.let { binding.webView.loadUrl(it) }
//            binding.progressBar.visibility = View.GONE
        }

//        args.articleUrl?.let { binding.webView.loadUrl(it) }



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