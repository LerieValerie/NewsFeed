package com.lerie_valerie.newsfeed.data.remote.retrofit

import com.google.gson.annotations.SerializedName

data class FeedResponse(
    @SerializedName("articles") val articleResponseList: List<ArticleResponse> = listOf()
) {
    fun toArticleList(key: Int) = articleResponseList.mapIndexed { i: Int, articleResponse: ArticleResponse ->
        articleResponse.toEntity(i + 1, key)
    }
}