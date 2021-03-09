package com.lerie_valerie.newsfeed.data.remote.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface NetInterface {
    @GET("/v2/everything")
    suspend fun getNewsFeed(
        @Query("q") q: String?,
        @Query("from") from: String?,
        @Query("sortBy") sortBy: String?,
        @Query("apiKey") apiKey: String?,
        @Query("page") page: Int?,
        @Query("pageSize") pageSize: Int
    ): FeedResponse
}