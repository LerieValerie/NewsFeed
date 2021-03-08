package com.lerie_valerie.newsfeed.presentation.roster

//import androidx.core.view.isVisible

//import com.lerie_valerie.newsfeed.databinding.FragmentNewsFeedRosterBinding
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
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
import com.lerie_valerie.newsfeed.R
import com.lerie_valerie.newsfeed.databinding.FragmentNewsFeedRosterBinding
import com.lerie_valerie.newsfeed.domain.repository.EventRepository
import com.lerie_valerie.newsfeed.logg
import com.lerie_valerie.newsfeed.presentation.view.ArticleView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield


@AndroidEntryPoint
class NewsFeedRosterFragment : Fragment() {
    private val viewModel: NewsFeedRosterViewModel by viewModels()

    private lateinit var adapterNews: NewsFeedAdapter
    private lateinit var adapterLoader: NewsFeedLoadingAdapter

    private lateinit var binding: FragmentNewsFeedRosterBinding

    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout?>? = null
    private var bIsLoaderAdapterItemRemoved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        adapterInit()
//        adapterLoaderInit()
//        setAdapterLoader()
//        setEventObservation()
        setArticleObservation()
//        adapterStateInit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsFeedRosterBinding.inflate(inflater, container, false)
        .apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        println("onChanged Start ${bottomSheetBehavior?.state}")
//        lifecycleScope.launch {
//            logg("asd vcreated")
//            adapterNews.loadStateFlow.onCompletion {
//                logg("asd complete")
//            }.asLiveData().observe(viewLifecycleOwner) {
//                logg("asd collect")
//            }
//        }
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
        )

        setEventObservation()
//        setLoadStateFlow()

        setBottomSheetBehaviour()
        adapterStateInit()
//        test()

//        println("onChanged End ${bottomSheetBehavior?.state}")
    }

    private fun display(article: ArticleView) {
        findNavController()
            .navigate(
                NewsFeedRosterFragmentDirections.actionDetail(
                    article.url
                )
            )
    }

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
//                    adapterLoader.notifyItemRemoved(0)
//                    bIsLoaderAdapterItemRemoved = true
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

//    private val fff = MutableStateFlow<CombinedLoadStates?>(null)

//        private fun setLoadStateFlow() {
//        lifecycleScope.launch {
//            adapterNews.loadStateFlow
//                .distinctUntilChangedBy { it.refresh }
//                .filter { it.refresh is LoadState.NotLoading }
//                .collect {
//                    binding.article.scrollToPosition(0)
////                    if (bIsLoaderAdapterItemRemoved) {
////                        adapterLoader.notifyItemInserted(0)
////                    }
//                }
//        }
//    }

    private fun test() {
//        adapterNews.addLoadStateListener { loadState ->
//            binding.progressBar.isVisible = loadState.refresh is Loading
//        }
    }

    private fun adapterStateInit() {


//        lifecycleScope.launchWhenResumed {
//            adapterNews.addLoadStateListener { loadState ->
//
//                println("sfa ${loadState.mediator?.refresh}")
////                if (loadState.refresh is Loading) {
////                    println("sfa")
////                }
////                binding.progressBar.isVisible = loadState.refresh is Loading
//            }
            adapterNews.loadStateFlow.debounce(100).asLiveData().observe(viewLifecycleOwner) { loadState ->
                binding.progressBar.isVisible = loadState.refresh is Loading

                val errorState =
//                loadState.source.append as? Error
//                ?: loadState.source.prepend as? Error
//                ?:
                    loadState.append as? Error
                        ?: loadState.prepend as? Error
                        ?: loadState.refresh as? Error

//                if (errorState == null) {
//                    return@collect
//                }
//                return@collect
//                println("onChanged0 ${bottomSheetBehavior?.state}")
//                println("onChanged ${errorState?.error}")
//                if (errorState != null) {
//                    binding.errorText.text = "${errorState.error}"
//                }
//                delay(100)
//                yield()
                if (errorState != null) {
                    println("onChanged1 ${bottomSheetBehavior?.state}")
                    binding.errorText.text = "${errorState.error}"
//                    if (bottomSheetBehavior?.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
//                    }

                    binding.btnRetry.setOnClickListener {
                        adapterNews.retry()
//                        println("onChanged5 ${bottomSheetBehavior?.state}")
//                        if (bottomSheetBehavior?.state != BottomSheetBehavior.STATE_HIDDEN) {
                        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
//                        }
                    }
                } else {
//                    println("onChanged2 ${bottomSheetBehavior?.state}")

//                    if (bottomSheetBehavior?.state != BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
//                    }
//                    println("onChanged3 ${bottomSheetBehavior?.state}")
                }
//                println("onChanged4 ${bottomSheetBehavior?.state}")
            }
//        }
    }

    private fun setBottomSheetBehaviour() {

        binding.bottomSheetLayout?.let {
            BottomSheetBehavior.from(it)?.let { bsb ->
                bsb.state = BottomSheetBehavior.STATE_HIDDEN
                bsb.saveFlags = BottomSheetBehavior.SAVE_ALL
//                bsb.saveFlags = BottomSheetBehavior.SAVE_PEEK_HEIGHT
//                bsb.saveFlags = BottomSheetBehavior.SAVE_FIT_TO_CONTENTS
//                bsb.saveFlags = BottomSheetBehavior.SAVE_HIDEABLE
//                bsb.saveFlags = BottomSheetBehavior.SAVE_SKIP_COLLAPSED
                binding.btnRetry2.setOnClickListener {
                    lifecycleScope.launch {
                        delay(1000)
                        println("onChanged Start ${bsb?.state}")
                        bsb.state = BottomSheetBehavior.STATE_EXPANDED
//                    if (bsb.state == BottomSheetBehavior.STATE_HIDDEN) {
//                        println("onChanged if ${bsb?.state}")
//                        bsb.state = BottomSheetBehavior.STATE_EXPANDED
//                        bsb.state = BottomSheetBehavior.STATE_EXPANDED
//                    }
//                    else {
//                        println("onChanged else ${bsb?.state}")
//                        bsb.state = BottomSheetBehavior.STATE_HIDDEN
//                    }
                        println("onChanged End ${bsb?.state}")
                    }
                }


                bottomSheetBehavior = bsb
            }
        }
    }

//    private fun setLoadStateFlow() {
//        lifecycleScope.launch {
//            adapterNews.loadStateFlow
//                .distinctUntilChangedBy { it.refresh }
//                .filter { it.refresh is LoadState.NotLoading }
//                .collect {
//                    binding.article.scrollToPosition(0)
////                    if (bIsLoaderAdapterItemRemoved) {
////                        adapterLoader.notifyItemInserted(0)
////                    }
//                }
//        }
//    }
}