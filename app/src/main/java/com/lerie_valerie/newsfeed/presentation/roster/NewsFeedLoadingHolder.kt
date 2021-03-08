package com.lerie_valerie.newsfeed.presentation.roster

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lerie_valerie.newsfeed.R
import com.lerie_valerie.newsfeed.databinding.LoadingStateBinding

class NewsFeedLoadingHolder(
    private val binding: LoadingStateBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

//    init {
//        binding.btnRetry.setOnClickListener {
//            retry()
//        }
//    }

    fun bind(loadState: LoadState) {
        binding.apply {
//            if (loadState is LoadState.Error) {
//                tvErrorMessage.text = loadState.error.localizedMessage
//            }
            progressBar.isVisible = loadState is LoadState.Loading
//            tvErrorMessage.isVisible = loadState !is LoadState.Loading
//            btnRetry.isVisible = loadState !is LoadState.Loading
//            tvErrorMessage.isVisible = false
//            btnRetry.isVisible = false
        }
    }
}
