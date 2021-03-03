package com.lerie_valerie.newsfeed.data.remote.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    const val BASE_API_URL =
        "https://newsapi.org"

    fun buildApi(): NetInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NetInterface::class.java)
    }
}