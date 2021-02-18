package com.lerie_valerie.newsfeed.presentation.roster

import androidx.recyclerview.widget.RecyclerView
import com.lerie_valerie.newsfeed.databinding.NewsItemBinding
import com.lerie_valerie.newsfeed.domain.entity.Article

class NewsFeedViewHolder(
        private val binding: NewsItemBinding,
        val onRowClick: (Article) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var article: Article

        init {
        binding.root.setOnClickListener { onRowClick(article) }
    }

    fun bind(article: Article) {
        this.article = article
        binding.apply {
            articleTitle.text = article.title
            articleDate.text = article.date
            articleDescription.text = article.description
        }
    }
}
