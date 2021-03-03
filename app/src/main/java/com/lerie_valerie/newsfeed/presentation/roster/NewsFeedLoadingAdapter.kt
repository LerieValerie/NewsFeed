package com.lerie_valerie.newsfeed.presentation.roster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lerie_valerie.newsfeed.databinding.LoadingStateBinding
import com.lerie_valerie.newsfeed.databinding.NewsItemBinding

class NewsFeedLoadingAdapter(
    private val inflater: LayoutInflater,
    private val retry: () -> Unit
) :
    LoadStateAdapter<NewsFeedLoadingHolder>() {

    override fun onBindViewHolder(holder: NewsFeedLoadingHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = NewsFeedLoadingHolder (
        LoadingStateBinding.inflate(inflater, parent, false),
        retry
    )
}