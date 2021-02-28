package com.lerie_valerie.newsfeed.data.remote.coil.repository

import android.graphics.Bitmap

interface BitmapSaveRepository {
    fun saveBitmapToStorage(bitmap: Bitmap, imageName: String)
}