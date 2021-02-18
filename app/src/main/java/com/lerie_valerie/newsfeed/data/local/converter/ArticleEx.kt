package com.lerie_valerie.newsfeed.data.local.converter

import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.domain.entity.Article

fun Article.toModel() = ArticleModel(
        id = id,
        title = title,
        description = description,
        date = date,
        urlToImage = urlToImage,
        url = url
)

fun ArticleModel.toEntity() = Article(
        id = id,
        title = title,
        description = description,
        date = date,
        urlToImage = urlToImage,
        url = url
)