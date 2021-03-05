package com.lerie_valerie.newsfeed.presentation.roster

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lerie_valerie.newsfeed.databinding.NewsItemBinding
import com.lerie_valerie.newsfeed.domain.entity.Article
import com.lerie_valerie.newsfeed.presentation.view.ArticleView

class NewsFeedAdapter(
        private val inflater: LayoutInflater,
        private val onRowClick: (ArticleView) -> Unit
) :
        PagingDataAdapter<ArticleView, NewsFeedViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            NewsFeedViewHolder (
            NewsItemBinding.inflate(inflater, parent, false),
                    onRowClick
    )

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}


private object DiffCallback : DiffUtil.ItemCallback<ArticleView>() {
    override fun areItemsTheSame(oldItem: ArticleView, newItem: ArticleView) =
            oldItem.id == newItem.id &&
                    oldItem.key == newItem.key

    override fun areContentsTheSame(oldItem: ArticleView, newItem: ArticleView) =
        oldItem == newItem

//        oldItem.title == newItem.title &&
//                oldItem.description == newItem.description &&
//                oldItem.date == newItem.date &&
//                oldItem.imageName == newItem.imageName
}
