package com.lerie_valerie.newsfeed.presentation.roster

import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import com.lerie_valerie.newsfeed.databinding.NewsItemBinding
import com.lerie_valerie.newsfeed.domain.entity.Article

class NewsFeedViewHolder(
        private val binding: NewsItemBinding,
        private val onRowClick: (Article) -> Unit,
        private val imageShow: (Article) -> Bitmap?
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var article: Article

        init {
        binding.root.setOnClickListener { onRowClick(article) }
    }

    fun bind(article: Article) {
        println("${article.id} ${article.key} article")
        this.article = article
        binding.apply {
            articleTitle.text = article.title
            articleDate.text = article.date
            articleDescription.text = article.description
            articleKey.text = "${article.id} ${article.key}"
            articleImage.setImageBitmap(imageShow(article))
        }
    }
}
