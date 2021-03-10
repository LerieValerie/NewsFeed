package com.lerie_valerie.newsfeed.presentation.roster

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
import com.lerie_valerie.newsfeed.presentation.view.ArticleView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@FlowPreview
@AndroidEntryPoint
class NewsFeedRosterFragment : Fragment() {
    private val viewModel: NewsFeedRosterViewModel by viewModels()

    private lateinit var adapterNews: NewsFeedAdapter
    private lateinit var adapterLoader: NewsFeedLoadingAdapter
    private lateinit var binding: FragmentNewsFeedRosterBinding

    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        adapterInit()
        setArticleObservation()
        setEventObservation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsFeedRosterBinding.inflate(inflater, container, false)
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

        adapterLoaderInit()
        setBottomSheetBehaviour()
        setBtnRetryListener()
        adapterStateInit()

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
        adapterLoader = NewsFeedLoadingAdapter(layoutInflater)
        binding.article.adapter = adapterNews.withLoadStateFooter(
            footer = adapterLoader
        )
    }

    private fun setEventObservation() {
        lifecycleScope.launchWhenResumed {
            viewModel.events.collect {
                when (it) {
                    is EventRepository.Event.ClearDatabase -> {
                        adapterNews.refresh()
                    }
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
        adapterNews.loadStateFlow.debounce(100).asLiveData()
            .observe(viewLifecycleOwner) { loadState ->

                binding.progressBar.isVisible =
                    loadState.refresh is Loading && adapterLoader.itemCount == 0

                val errorState =
                    loadState.append as? Error
                        ?: loadState.prepend as? Error
                        ?: loadState.refresh as? Error

                if (errorState != null) {
                    val errorMessage = errorState.error.message?.let { getErrorMessage(it) }

                    binding.errorText.text = errorMessage

                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
                } else {
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
    }

    private fun getErrorMessage(error: String) =
        when {
            error.contains("426") -> {
                getString(R.string.error_426)
            }
            error.contains("429") -> {
                getString(R.string.error_429)
            }
            error.contains("401") -> {
                getString(R.string.error_401)
            }
            else -> {
                error
            }
        }


    private fun setBottomSheetBehaviour() {
        binding.bottomSheetLayout.let {
            BottomSheetBehavior.from(it).let { bsb ->
                bsb.state = BottomSheetBehavior.STATE_HIDDEN
                bsb.saveFlags = BottomSheetBehavior.SAVE_ALL

                bottomSheetBehavior = bsb
            }
        }
    }

    private fun setBtnRetryListener() {
        binding.btnRetry.setOnClickListener {
            adapterNews.retry()
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }
}