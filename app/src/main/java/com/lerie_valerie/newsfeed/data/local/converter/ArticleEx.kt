package com.lerie_valerie.newsfeed.data.local.converter

import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.domain.entity.Article
import kotlinx.datetime.Instant

fun Article.toModel() = ArticleModel(
        id = id,
        key = key,
        title = title,
        description = description,
        date = date?.toLong(),
        urlToImage = urlToImage,
        imageName = imageName,
        url = url
)

fun ArticleModel.toEntity() = Article(
        id = id,
        key = key,
        title = title,
        description = description,
        date = date?.toInstant(),
        urlToImage = urlToImage,
        imageName = imageName,
        url = url
)

private fun Instant.toLong(): Long = this.toEpochMilliseconds()

private fun Long.toInstant() = this.let {
        Instant.fromEpochMilliseconds(it)
}