package com.lerie_valerie.newsfeed.presentation.roster

//import androidx.core.view.isVisible

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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.lerie_valerie.newsfeed.R
import com.lerie_valerie.newsfeed.databinding.FragmentNewsFeedRosterBinding
import com.lerie_valerie.newsfeed.domain.repository.EventRepository
import com.lerie_valerie.newsfeed.presentation.view.ArticleView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged


@AndroidEntryPoint
class NewsFeedRosterFragment : Fragment() {
    private val viewModel: NewsFeedRosterViewModel by viewModels()

    private lateinit var adapterNews: NewsFeedAdapter
    private lateinit var adapterLoader: NewsFeedLoadingAdapter

    private lateinit var binding: FragmentNewsFeedRosterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        adapterInit()
//        adapterLoaderInit()
//        setAdapterLoader()
//        setEventObservation()
        setArticleObservation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  = FragmentNewsFeedRosterBinding.inflate(inflater, container, false)
            .apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



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

//        adapterLoaderInit()
        adapterLoader = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
        binding.article.adapter = adapterNews.withLoadStateFooter(
            footer = adapterLoader
//            footer = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
        )

        setEventObservation()

//    val a = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
//    a.da
//    binding.article.adapter = adapterNews.withLoadStateFooter(
//        footer = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
//    )

//        adapterLoader = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
//        binding.article.adapter = adapterNews.withLoadStateFooter(
//            footer = adapterLoader
////            footer = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
//        )
        adapterStateInit()
        setBottomSheetBehaviour()
//        binding.progressBarMain.visibility = View.VISIBLE
//        lifecycleScope.launch {
//            viewModel.loadArticle().distinctUntilChanged().collectLatest { pagingData ->
//                adapterNews.submitData(pagingData)
//            }
//        }

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
                viewModel.clearAll()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun adapterInit() {
        adapterNews =
            NewsFeedAdapter(
                layoutInflater,
                onRowClick = ::display
            )

        adapterNews.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun adapterLoaderInit() {
        adapterLoader = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
    }

    private fun setAdapterLoader() {
        adapterNews.withLoadStateFooter(
            footer = adapterLoader
        )
    }

    private fun setEventObservation() {
        viewModel.events.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is EventRepository.Event.ClearDatabase -> {
                    println("eventRepository")
                    adapterLoader.notifyItemRemoved(0)
                    adapterNews.refresh()
                }
            }
        }
    }

    private fun setArticleObservation() {
        lifecycleScope.launchWhenResumed {
            viewModel.loadArticle().distinctUntilChanged().collectLatest { pagingData ->
                adapterNews.submitData(pagingData)
            }
        }
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
//            binding.btnRetry.setOnClickListener { adapterNews.retry() }

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

    private fun setBottomSheetBehaviour() {
//        val bottomSheetBehavior: BottomSheetBehavior<*> =
//            BottomSheetBehavior.from<View>(llBottomSheet)

// настройка состояний нижнего экрана

// настройка состояний нижнего экрана
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

// настройка максимальной высоты

// настройка максимальной высоты
//        bottomSheetBehavior.peekHeight = 340

// настройка возможности скрыть элемент при свайпе вниз

// настройка возможности скрыть элемент при свайпе вниз
//        bottomSheetBehavior.isHideable = false

// настройка колбэков при изменениях

// настройка колбэков при изменениях
//        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {}
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
//        })



//        val fragment = supportFragmentManager.findFragmentById(R.id.filter_fragment)

        binding.bottomSheetLayout?.let {
            // Get the BottomSheetBehavior from the fragment view
            BottomSheetBehavior.from(it)?.let { bsb ->
                // Set the initial state of the BottomSheetBehavior to HIDDEN
                bsb.state = BottomSheetBehavior.STATE_HIDDEN

                binding.btnRetry.setOnClickListener {
                    bsb.state = BottomSheetBehavior.STATE_EXPANDED
                }

                // Set the trigger that will expand your view
//                fab_filter.setOnClickListener { bsb.state = BottomSheetBehavior.STATE_EXPANDED }

                // Set the reference into class attribute (will be used latter)
//                mBottomSheetBehavior = bsb
            }
        }
    }
}