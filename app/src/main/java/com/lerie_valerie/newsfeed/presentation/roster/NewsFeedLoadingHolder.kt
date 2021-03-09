package com.lerie_valerie.newsfeed.presentation.roster

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.lerie_valerie.newsfeed.databinding.LoadingStateBinding

class NewsFeedLoadingHolder(
    private val binding: LoadingStateBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.apply {
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }
}
