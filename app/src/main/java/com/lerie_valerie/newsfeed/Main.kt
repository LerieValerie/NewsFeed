package com.lerie_valerie.newsfeed

import com.lerie_valerie.newsfeed.data.remote.RetrofitBuilder
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val a = RetrofitBuilder.buildApi(RetrofitBuilder.BASE_API_URL)
        val res = a.getNewsFeed(
            q = Constant.C_Query,
            from = Constant.C_From,
            sortBy = Constant.C_Sort,
            apiKey = Constant.C_ApiKey,
            page = 1,
            pageSize = 20
        )

        println()
    }
}