package com.lerie_valerie.newsfeed.domain.entity

import kotlinx.datetime.Instant

data class Article(
        val id: Int,
        val title: String?,
        val description: String?,
        val date: String?,
//        val date: Instant,
        val urlToImage: String?,
        val url: String?
)