package com.lerie_valerie.newsfeed.presentation.roster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lerie_valerie.newsfeed.databinding.NewsItemBinding
import com.lerie_valerie.newsfeed.domain.entity.Article

class NewsFeedAdapter(
        private val inflater: LayoutInflater,
        private val onRowClick: (Article) -> Unit) :
        PagingDataAdapter<Article, NewsFeedViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            NewsFeedViewHolder (
            NewsItemBinding.inflate(inflater, parent, false),
                    onRowClick
    )

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}


private object DiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.id == newItem.id &&
                    oldItem.key == newItem.key

    override fun areContentsTheSame(oldItem: Article, newItem: Article) =
        oldItem == newItem
//            oldItem.title == newItem.title &&
//                    oldItem.urlToImage == newItem.urlToImage
}


//class DiffUtilCallBack : DiffUtil.ItemCallback<RedditPost>() {
//    override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
//        return oldItem.key == newItem.key
//    }
//
//    override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
//        return oldItem.key == newItem.key
//                && oldItem.score == newItem.score
//                && oldItem.commentCount == newItem.commentCount
//    }
//}