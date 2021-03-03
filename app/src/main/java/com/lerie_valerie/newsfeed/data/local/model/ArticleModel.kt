package com.lerie_valerie.newsfeed.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

//@Entity(tableName = "article")
@Entity(tableName = "article", primaryKeys = ["id", "key"])
data class ArticleModel(
//    @PrimaryKey
    val id: Int,
    val key: Int,
    val title: String?,
    val description: String?,
    val date: Long?,
    val urlToImage: String?,
    val imageName: String?,
    val url:String?
)