package com.lerie_valerie.newsfeed.data.remote.coil

import android.content.Context
import coil.request.ImageRequest
import com.lerie_valerie.newsfeed.data.remote.retrofit.NetInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoilBuilder {

    fun build(context: Context) =
        ImageRequest.Builder(context)

}
