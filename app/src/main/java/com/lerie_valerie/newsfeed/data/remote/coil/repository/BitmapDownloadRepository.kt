package com.lerie_valerie.newsfeed.data.remote.coil.repository

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.request.ImageRequest

interface BitmapDownloadRepository {
    suspend fun getBitmapDownload(request: ImageRequest): Bitmap?
}