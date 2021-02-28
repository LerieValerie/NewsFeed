package com.lerie_valerie.newsfeed.data.remote.coil

import coil.request.ImageRequest
import javax.inject.Inject

class CoilRequest @Inject constructor(private val request: ImageRequest.Builder) {

    fun getImageRequest(imageUrl: String) =
        request.data(imageUrl).build()
}