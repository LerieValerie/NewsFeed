package com.lerie_valerie.newsfeed.data.coil.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.ImageResult
import com.lerie_valerie.newsfeed.domain.repository.BitmapRepository
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class BitmapRepositoryImpl @Inject constructor(
    private val imageLoader: ImageLoader,
    private val context: Context
) : BitmapRepository {

    override suspend fun getBitmapDownload(request: ImageRequest): Bitmap? =
        (imageLoader.execute(request).drawable as BitmapDrawable).bitmap

//    override suspend fun getBitmapDownload(request: ImageRequest): Bitmap? {
//        val image = imageLoader.execute(request)
////        println(image)
//        return (image.drawable as BitmapDrawable).bitmap
//    }

//        try {
//            val image = imageLoader.execute(request)
////                .drawable
////                    as BitmapDrawable
//                image.bitmap
//        }
//        catch (e: Throwable) {
//            ErrorResult()
//        }


    override suspend fun saveBitmapToStorage(bitmap: Bitmap, imageName: String) {

//        val dirPath = context.filesDir.absolutePath.toString() + File.separator + "Images"
        val pathDir = getPathDir()
        val fileImage = File(pathDir)
        if(!fileImage.exists()) {
            fileImage.mkdir();
//            Log.i("IO", ""+folder.exists());
        }

//        var fos: OutputStream? = null
        val image = File(pathDir, imageName)
        val fos = FileOutputStream(image)
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

    override fun getBitmapFromStorage(imageName: String?): Bitmap? {
        val pathDir = getPathDir()
        val file: File = File(
            pathDir,
            imageName
        )
        return BitmapFactory.decodeFile(file.absolutePath)
    }

    override fun deleteBitmapFolder() {
        val pathDir = getPathDir()
        val fileImage = File(pathDir)
        if (fileImage.exists()) {
//            fileImage.delete()
            fileImage.deleteRecursively()
        }

//        val a = context.cacheDir.absolutePath.toString() + File.separator + "image_cache"
//        val file = File(a)
//        if (file.exists()) {
//            file.deleteRecursively()
//        }
    }

    private fun getPathDir() =
//        context.cacheDir.absolutePath.toString() + File.separator + "Images"
        context.filesDir.absolutePath.toString() + File.separator + "Images"
}