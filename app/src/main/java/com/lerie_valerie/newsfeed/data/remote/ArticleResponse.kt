package com.lerie_valerie.newsfeed.data.remote

import com.google.gson.annotations.SerializedName
import com.lerie_valerie.newsfeed.domain.entity.Article

data class ArticleResponse(
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("publishedAt") val date: String?,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("url") val url: String?
) {
    fun toEntity(id: Int): Article = Article(
            id = id,
            title = title,
            description = description,
            date = date,
            urlToImage = urlToImage,
            url = url
    )
}