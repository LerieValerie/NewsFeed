package com.lerie_valerie.newsfeed.presentation.view

import com.lerie_valerie.newsfeed.domain.entity.Article
import com.lerie_valerie.newsfeed.presentation.formatter.DateTimeFormatter

data class ArticleView (
    val id: Int,
    val key: Int,
    val title: String?,
    val description: String?,
    val date: String?,
    val imageName: String?,
    val url: String?
)

fun Article.toView() = ArticleView(
    id = id,
    key = key,
    title = title,
    description = description,
    date = DateTimeFormatter.getDateWithDot(date),
    imageName = imageName,
    url = url
)