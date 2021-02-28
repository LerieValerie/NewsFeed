package com.lerie_valerie.newsfeed.data.remote.coil.repository

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import javax.inject.Inject

class BitmapDownloadRepositoryImpl @Inject constructor(private val imageLoader: ImageLoader) :
    BitmapDownloadRepository{

    override suspend fun getBitmapDownload(request: ImageRequest): Bitmap? =
        (imageLoader.execute(request).drawable as BitmapDrawable).bitmap
}