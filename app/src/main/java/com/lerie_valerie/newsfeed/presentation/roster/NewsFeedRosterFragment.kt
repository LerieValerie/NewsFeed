package com.lerie_valerie.newsfeed.presentation.roster

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.lerie_valerie.newsfeed.databinding.FragmentNewsFeedRosterBinding
import com.lerie_valerie.newsfeed.domain.entity.Article
import com.lerie_valerie.newsfeed.presentation.view.ArticleView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFeedRosterFragment: Fragment() {
    private val viewModel: NewsFeedRosterViewModel by viewModels()
    private lateinit var imageLoader: ImageLoader

    //    by viewModel()
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

        val adapter =
                NewsFeedAdapter(
                        layoutInflater,
                        onRowClick = ::display,
                    imageShow = ::imageShow
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

        lifecycleScope.launch {
            //Your adapter's loadStateFlow here
            adapter.loadStateFlow.distinctUntilChangedBy {
                it.refresh
            }.collectLatest {
                val list = adapter.snapshot()
            }
        }


//        viewModel.loadArticleLiveData().observe(viewLifecycleOwner) {
//            adapter.submitData(lifecycle, it)
//        }
//        binding.article.scrollToPosition(5)
//
//        binding.article.adapter = adapter
//        binding.article.adapter = adapter.withLoadStateFooter(
//            footer = NewsFeedLoadingAdapter(layoutInflater) { adapter.retry() }
//        )
        binding.article.adapter = adapter.withLoadStateHeaderAndFooter(
            header = NewsFeedLoadingAdapter(layoutInflater) { adapter.retry() },
            footer = NewsFeedLoadingAdapter(layoutInflater) { adapter.retry() }
        )


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

    private fun display(article: ArticleView) {
        findNavController()
                .navigate(
                        NewsFeedRosterFragmentDirections.actionDetail(
                            article.url
                        )
                )
    }

    private fun imageShow(article: ArticleView) : Bitmap? =
        viewModel.getImageFromStorage(article.imageName)

//    private fun setupViews() {
//        binding.article.adapter = redditAdapter
//        binding.article.adapter = redditAdapter.withLoadStateHeaderAndFooter(
//            header = NewsFeedLoadingAdapter { redditAdapter.retry() },
//            footer = NewsFeedLoadingAdapter { redditAdapter.retry() }
//        )
//    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putString("qq", binding.article.text.trim().toString())
//    }

//    private fun getBitmapFromUrl(imageUrl: String, imageView: ImageView) = lifecycleScope.launch {
////        progressbar.visible(true)
//        imageView.load(imageUrl)
//        val imageRequest = ImageRequest.Builder(requireContext())
//            .data(imageUrl)
//            .build()
//        try {
//            val downloadBitmap = (imageLoader.execute(imageRequest).drawable as BitmapDrawable).bitmap
//            imageView.setImageBitmap(downloadBitmap)
//            saveMediaToStorage(downloadedBitmap)
//        } catch (e: Exception) {
//            toast(e.message)
//        }
////        progressbar.visible(false)
//    }
}