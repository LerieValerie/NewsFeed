package com.lerie_valerie.newsfeed.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "article")
data class ArticleModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String?,
    val description: String?,
    val date: String?,
//    val date: Long?,
    val urlToImage: String?,
    val url:String?
)