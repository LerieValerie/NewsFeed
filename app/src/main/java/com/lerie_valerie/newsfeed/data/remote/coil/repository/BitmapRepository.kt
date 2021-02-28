package com.lerie_valerie.newsfeed.data.remote.coil.repository

import android.graphics.Bitmap
import coil.request.ImageRequest

interface BitmapRepository {
    suspend fun getBitmapDownload(request: ImageRequest): Bitmap?
    fun saveBitmapToStorage(bitmap: Bitmap, imageName: String)
}