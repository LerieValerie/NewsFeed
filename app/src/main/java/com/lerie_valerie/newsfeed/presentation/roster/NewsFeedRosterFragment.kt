package com.lerie_valerie.newsfeed.presentation.roster

//import androidx.core.view.isVisible

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoader
import com.lerie_valerie.newsfeed.R
import com.lerie_valerie.newsfeed.databinding.FragmentNewsFeedRosterBinding
import com.lerie_valerie.newsfeed.presentation.view.ArticleView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewsFeedRosterFragment : Fragment() {
    private val viewModel: NewsFeedRosterViewModel by viewModels()
    private lateinit var imageLoader: ImageLoader
    private lateinit var adapterNews: NewsFeedAdapter
    private lateinit var adapterLoader: NewsFeedLoadingAdapter
//    private lateinit var snackBarError: Snackbar

    private lateinit var binding: FragmentNewsFeedRosterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        imageLoader = Coil.imageLoader(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentNewsFeedRosterBinding.inflate(inflater, container, false)
            .apply { binding = this }.root

        return view
    }

//    @OptIn(InternalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//    viewModel..asLiveData().observe(viewLifecycleOwner)


        adapterNews =
            NewsFeedAdapter(
                layoutInflater,
                onRowClick = ::display
//                imageShow = ::imageShow
            )

        adapterNews.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

    viewModel.events.asLiveData().observe(viewLifecycleOwner) {
        println("eventRepository")
        adapterLoader.notifyItemRemoved(0)
        adapterNews.refresh()
    }
//
//    adapterNews.addLoadStateListener { loadState ->
//        binding.progressBar.isVisible = loadState.refresh is Loading
//    }

//    adapterNews.loadStateFlow
//        adapterNews.addLoadStateListener { loadState ->
//
//            binding.apply {
//
//                val errorState =
////                    loadState.source.append as? LoadState.Error
////                    ?: loadState.source.prepend as? LoadState.Error
//                    loadState.refresh as? LoadState.Error
////                    ?: loadState.append as? LoadState.Error
////                    ?: loadState.prepend as? LoadState.Error
//
//                errorState?.let {
//                    tvErrorMessage.text = "${it.error.localizedMessage}"
//                }
////                    Toast.makeText(
////                        this,
////                        "\uD83D\uDE28 Wooops ${it.error}",
////                        Toast.LENGTH_LONG
////                    ).show()
////                }
////
////                errorState?.let {
////                    Toast.makeText(
////                        this,
////                        "\uD83D\uDE28 Wooops ${it.error}",
////                        Toast.LENGTH_LONG
////                    ).show()
////                }
////                tvErrorMessage.text = errorState.toString()
//
////                progressBar.isVisible = loadState.source.refresh is Loading
//
//                binding.article.isVisible = loadState.source.refresh !is Loading
//                progressBar.isVisible = loadState.refresh is Loading
////                progressBar.isVisible = loadState is loaLoading
//                tvErrorMessage.isVisible = loadState.refresh !is Loading
//                btnRetry.isVisible = loadState.refresh is Error
//            }
//        }


//    snackBarError = Snackbar.make(
//        binding.layoutMain,
//        "",
//        Snackbar.LENGTH_INDEFINITE
//    )
//    adapterStateInit()

        binding.article.apply {
            adapter = adapterNews
            layoutManager = LinearLayoutManager(context)

            addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                )
            )


        }

//    val a = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
//    a.da
//    binding.article.adapter = adapterNews.withLoadStateFooter(
//        footer = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
//    )

    adapterLoader = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
    binding.article.adapter = adapterNews.withLoadStateFooter(
        footer = adapterLoader
//            footer = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
    )
    adapterStateInit()
//        binding.progressBarMain.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.loadArticle().distinctUntilChanged().collectLatest { pagingData ->
                adapterNews.submitData(pagingData)
            }
//            binding.progressBarMain.visibility = View.GONE
        }

//    adapterLoader = NewsFeedLoadingAdapter(layoutInflater, requireContext()) { adapterNews.retry() }
//    binding.article.adapter = adapterNews.withLoadStateFooter(
//        footer = adapterLoader
////            footer = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
//    )

//        viewModel.loadArticleLiveData().observe(viewLifecycleOwner) {
//            adapterNews.submitData(lifecycle, it)
//        }


    }

    private fun display(article: ArticleView) {
        findNavController()
            .navigate(
                NewsFeedRosterFragmentDirections.actionDetail(
                    article.url
                )
            )
    }

//    private fun imageShow(article: ArticleView): Bitmap? =
//        viewModel.getImageFromStorage(article.imageName)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.load -> {
//                binding.progressBar.visibility = View.VISIBLE
                viewModel.clearAll()
//                adapterLoader.notifyItemRemoved(0)
//                adapterNews.refresh()
//                binding.progressBarMain.visibility = View.GONE
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun adapterStateInit() {
        adapterNews.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.refresh is Loading

            // Only show the list if refresh succeeds.
//            binding.article.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.refresh is Loading
            // Show the retry state if initial load or refresh fails.
            binding.btnRetry.isVisible = loadState.refresh is Error
            binding.btnRetry.setOnClickListener{adapterNews.retry()}

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? Error
                ?: loadState.source.prepend as? Error
                ?: loadState.append as? Error
                ?: loadState.prepend as? Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }

//            val errorState =
//                loadState.refresh as? Error
//                    ?: loadState.append as? Error
//                    ?: loadState.prepend as? Error
//
//            val errorText = errorState?.let { "${it.error.localizedMessage}" }
//
////            val snackBarError = Snackbar.make(
////                binding.layoutMain,
////                "",
////                Snackbar.LENGTH_LONG
////            )
//println("SNACK errorTextBefore ${errorText}")
//
//            if (errorText != null) {
//                val snackBarError = Snackbar.make(
//                    binding.layoutMain,
//                    errorText,
//                    10000
//                )
//    println("SNACK errorText ${errorText}")
//    snackBarError.setText(errorText)
////                snackBarError.duration = 5
//                snackBarError.setAction(R.string.snackbar_retry) {
//                    println("SNACK action")
//                    snackBarError.dismiss()
//                    println("SNACK show ${snackBarError.isShown}")
//                    adapterNews.retry()
//                    println("SNACK after action")
//                    }
//                    .show()
//                println("SNACK after show")
//            }
//            else {
//                println("SNACK errorText2 ${errorText} ${snackBarError.isShown}")
//                if (snackBarError.isShown) {
//                    snackBarError.dismiss()
//                }
//            }

//            val snackBarLoading = Snackbar.make(
//                binding.layoutMain,
//                getString(R.string.snackBarLoading),
//                Snackbar.LENGTH_INDEFINITE
//            )
//
//            val bIsAppendLoading = loadState.mediator?.append is Loading
//            if (bIsAppendLoading) {
//                val snackBarLayout:SnackbarLayout = snackBarLoading.view as SnackbarLayout
//                snackBarLayout.addView(ProgressBar(requireContext()))
//
//                snackBarLoading.show()
//            }
//            else {
//                if (snackBarLoading.isShown) {
//                    snackBarLoading.dismiss()
//                }
//            }
        }
    }
}