package com.lerie_valerie.newsfeed.data.local.model

import androidx.room.Entity

@Entity(tableName = "article", primaryKeys = ["id", "key"])
data class ArticleModel(
    val id: Int,
    val key: Int,
    val title: String?,
    val description: String?,
    val date: Long?,
    val urlToImage: String?,
    val url: String?
)