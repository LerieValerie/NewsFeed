package com.lerie_valerie.newsfeed.data.remote.coil.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class BitmapRepositoryImpl @Inject constructor(
    private val imageLoader: ImageLoader,
    private val context: Context
) : BitmapRepository {

    override suspend fun getBitmapDownload(request: ImageRequest): Bitmap? =
        (imageLoader.execute(request).drawable as BitmapDrawable).bitmap

    override fun saveBitmapToStorage(bitmap: Bitmap, imageName: String) {

        val dirPath = context.filesDir.absolutePath.toString() + File.separator + "Images"
        val image = File(dirPath, imageName)

//        var fos: OutputStream? = null
        val fos = FileOutputStream(image)
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }
}