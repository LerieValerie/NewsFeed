package com.lerie_valerie.newsfeed.presentation.roster

import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lerie_valerie.newsfeed.R
import com.lerie_valerie.newsfeed.databinding.NewsItemBinding
import com.lerie_valerie.newsfeed.domain.entity.Article
import com.lerie_valerie.newsfeed.presentation.view.ArticleView

class NewsFeedViewHolder(
        private val binding: NewsItemBinding,
        private val onRowClick: (ArticleView) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var article: ArticleView

        init {
        binding.root.setOnClickListener { onRowClick(article) }
    }

    fun bind(article: ArticleView) {
        println("${article.id} ${article.key} article")
        this.article = article
        binding.apply {
            articleTitle.text = article.title
            articleDate.text = article.date
            articleDescription.text = article.description
            articleKey.text = "${article.id} ${article.key}"

            articleImage.load(article.urlToImage) {
                placeholder(R.drawable.ic_image_place_holder)
                error(R.drawable.ic_broken_image)
                fallback(R.drawable.ic_no_image)
            }
//            imageView.load("https://www.example.com/image.jpg") {
//                crossfade(true)
//                placeholder(R.drawable.image)
//                transformations(CircleCropTransformation())
//            }
//            articleImage.setImageBitmap(imageShow(article))
        }
    }
}
