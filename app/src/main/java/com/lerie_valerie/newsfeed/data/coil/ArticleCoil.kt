package com.lerie_valerie.newsfeed.data.coil

import com.lerie_valerie.newsfeed.domain.entity.Article

data class ArticleCoil (
    val urlToImage: String?,
    val imageName: String?
)

fun Article.toCoil() = ArticleCoil(
    urlToImage = urlToImage,
    imageName = imageName
)
