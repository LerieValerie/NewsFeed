package com.lerie_valerie.newsfeed.presentation.roster

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
//import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadState.*
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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Error

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
    ): View {
        val view = FragmentNewsFeedRosterBinding.inflate(inflater, container, false)
            .apply { binding = this }.root

        return view
    }

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

        adapterNews.addLoadStateListener { loadState ->

            binding.apply {
//                if (loadState.refresh is Error) {
//                    tvErrorMessage.text = loadState..localizedMessage
//                }
                val errorState =
                    loadState.refresh as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error

                errorState?.let {
                    tvErrorMessage.text = "${it.error}"
                }
//                tvErrorMessage.text = errorState.toString()

//                progressBar.isVisible = loadState.source.refresh is Loading
                progressBar.isVisible = loadState.refresh is Loading
//                progressBar.isVisible = loadState is loaLoading
                tvErrorMessage.isVisible = loadState.refresh !is Loading
                btnRetry.isVisible = loadState.refresh is Error
            }
        }

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

//        binding.progressBarMain.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.loadArticle().distinctUntilChanged().collectLatest { pagingData ->
                println("asdasd $")
                adapterNews.submitData(pagingData)
            }
//            binding.progressBarMain.visibility = View.GONE
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
//                binding.progressBarMain.visibility = View.VISIBLE
                viewModel.clearAll()
                adapterNews.refresh()
//                binding.progressBarMain.visibility = View.GONE
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}