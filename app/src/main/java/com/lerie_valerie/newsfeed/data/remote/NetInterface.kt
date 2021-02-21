package com.lerie_valerie.newsfeed.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface NetInterface {
    //https://newsapi.org/v2/everything?q=android&from=2019-04-00&sortBy=publi shedAt&apiKey=26eddb253e7840f988aec61f2ece2907&page=

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