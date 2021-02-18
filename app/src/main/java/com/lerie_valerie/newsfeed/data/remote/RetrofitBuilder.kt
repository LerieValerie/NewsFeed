package com.lerie_valerie.newsfeed.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    const val BASE_API_URL =
        "https://newsapi.org"

    fun buildApi(baseUrl: String): NetInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NetInterface::class.java)
    }
}