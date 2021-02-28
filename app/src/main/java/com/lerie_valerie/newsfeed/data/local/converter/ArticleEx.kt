package com.lerie_valerie.newsfeed.data.local.converter

import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.domain.entity.Article

fun Article.toModel() = ArticleModel(
        id = id,
        key = key,
        title = title,
        description = description,
        date = date,
        urlToImage = urlToImage,
        imageName = imageName,
        url = url
)

fun ArticleModel.toEntity() = Article(
        id = id,
        key = key,
        title = title,
        description = description,
        date = date,
        urlToImage = urlToImage,
        imageName = imageName,
        url = url
)