package com.lerie_valerie.newsfeed.data.remote

import com.google.gson.annotations.SerializedName

data class FeedResponse(
//    @SerializedName("status") val status: String?,
//    @SerializedName("totalResults") val totalResult: Long?,
    @SerializedName("articles") val articleResponseList: List<ArticleResponse> = listOf()
) {
    fun toArticleList() = articleResponseList.mapIndexed { i: Int, articleResponse: ArticleResponse ->
        articleResponse.toEntity(i + 1)
    }
}