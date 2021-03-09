package com.lerie_valerie.newsfeed.data.remote.retrofit

import com.google.gson.annotations.SerializedName
import com.lerie_valerie.newsfeed.domain.entity.Article
import kotlinx.datetime.toInstant

data class ArticleResponse(
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("publishedAt") val date: String?,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("url") val url: String?
) {
    fun toEntity(id: Int, key: Int): Article = Article(
        id = id,
        key = key,
        title = title,
        description = description,
        date = date?.let { it.toInstant() },
        urlToImage = urlToImage,
        url = url
    )
}