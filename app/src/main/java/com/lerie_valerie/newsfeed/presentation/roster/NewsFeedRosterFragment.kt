package com.lerie_valerie.newsfeed.presentation.roster

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lerie_valerie.newsfeed.databinding.FragmentNewsFeedRosterBinding
import com.lerie_valerie.newsfeed.domain.entity.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFeedRosterFragment: Fragment() {
    private val viewModel: NewsFeedRosterViewModel by viewModels()

    //    by viewModel()
    private lateinit var binding: FragmentNewsFeedRosterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsFeedRosterBinding.inflate(inflater, container, false)
        .apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
                NewsFeedAdapter(
                        layoutInflater,
                        onRowClick = ::display
                )

        binding.article.apply {
            adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

            setAdapter(adapter)
            layoutManager = LinearLayoutManager(context)

            addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }


        lifecycleScope.launchWhenResumed {
            viewModel.loadArticle().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }


//        viewModel.loadArticleLiveData().observe(viewLifecycleOwner) {
//            adapter.submitData(lifecycle, it)
//        }
//        binding.article.scrollToPosition(5)
//
        binding.article.adapter = adapter
        binding.article.adapter = adapter.withLoadStateFooter(
            footer = NewsFeedLoadingAdapter(layoutInflater) { adapter.retry() }
        )
//        binding.article.adapter = adapter.withLoadStateHeaderAndFooter(
//            header = NewsFeedLoadingAdapter(layoutInflater) { adapter.retry() },
//            footer = NewsFeedLoadingAdapter(layoutInflater) { adapter.retry() }
//        )


//        viewModel.loadArticle().asLiveData().observe(viewLifecycleOwner) { pagingData ->
//            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
//        }

//        private fun fetchPosts() {
//            lifecycleScope.launch {
//                redditViewModel.fetchPosts().collectLatest { pagingData ->
//                    redditAdapter.submitData(pagingData)
//                }
//            }
//
//        }


    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.actions, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.load -> {
//                viewModel.reloadFromRemoteFailure()
//                viewModel.load()
//                return true
//            }
//            R.id.delete -> {
//                viewModel.delete()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    private fun fetchPosts() {
//        lifecycleScope.launch {
//            redditViewModel.fetchPosts().collectLatest { pagingData ->
//                redditAdapter.submitData(pagingData)
//            }
//        }
//
//    }

    private fun display(article: Article) {
        findNavController()
                .navigate(
                        NewsFeedRosterFragmentDirections.actionDetail(
                        )
                )
    }

//    private fun setupViews() {
//        binding.article.adapter = redditAdapter
//        binding.article.adapter = redditAdapter.withLoadStateHeaderAndFooter(
//            header = NewsFeedLoadingAdapter { redditAdapter.retry() },
//            footer = NewsFeedLoadingAdapter { redditAdapter.retry() }
//        )
//    }
}