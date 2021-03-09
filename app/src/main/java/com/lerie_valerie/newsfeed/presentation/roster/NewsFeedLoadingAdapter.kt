package com.lerie_valerie.newsfeed.presentation.roster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.lerie_valerie.newsfeed.databinding.LoadingStateBinding

class NewsFeedLoadingAdapter(
    private val inflater: LayoutInflater
) : LoadStateAdapter<NewsFeedLoadingHolder>() {

    override fun onBindViewHolder(holder: NewsFeedLoadingHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = NewsFeedLoadingHolder(
        LoadingStateBinding.inflate(inflater, parent, false)
    )
}