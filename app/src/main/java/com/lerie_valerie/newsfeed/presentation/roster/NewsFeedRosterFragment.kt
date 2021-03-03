package com.lerie_valerie.newsfeed.presentation.roster

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoader
import com.lerie_valerie.newsfeed.R
import com.lerie_valerie.newsfeed.databinding.FragmentNewsFeedRosterBinding
import com.lerie_valerie.newsfeed.presentation.view.ArticleView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class NewsFeedRosterFragment : Fragment() {
    private val viewModel: NewsFeedRosterViewModel by viewModels()
    private lateinit var imageLoader: ImageLoader
    private lateinit var adapterNews: NewsFeedAdapter

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
    ): View = FragmentNewsFeedRosterBinding.inflate(inflater, container, false)
        .apply { binding = this }.root

    @OptIn(InternalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterNews =
            NewsFeedAdapter(
                layoutInflater,
                onRowClick = ::display,
                imageShow = ::imageShow
            )

        adapterNews.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

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

        lifecycleScope.launchWhenResumed {
            viewModel.loadArticle().distinctUntilChanged().collectLatest { pagingData ->
                adapterNews.submitData(pagingData)
            }
        }

//        viewModel.loadArticleLiveData().observe(viewLifecycleOwner) {
//            adapterNews.submitData(lifecycle, it)
//        }

        binding.article.adapter = adapterNews.withLoadStateFooter(
            footer = NewsFeedLoadingAdapter(layoutInflater) { adapterNews.retry() }
        )
    }

    private fun display(article: ArticleView) {
        findNavController()
            .navigate(
                NewsFeedRosterFragmentDirections.actionDetail(
                    article.url
                )
            )
    }

    private fun imageShow(article: ArticleView): Bitmap? =
        viewModel.getImageFromStorage(article.imageName)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.load -> {
                viewModel.clearAll()
                adapterNews.refresh()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}