package com.lerie_valerie.newsfeed.data.coil.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.ImageResult
import com.lerie_valerie.newsfeed.data.coil.CoilRequest
import com.lerie_valerie.newsfeed.data.coil.toCoil
import com.lerie_valerie.newsfeed.data.local.converter.toModel
import com.lerie_valerie.newsfeed.domain.entity.Article
import com.lerie_valerie.newsfeed.domain.repository.BitmapRepository
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class BitmapRepositoryImpl @Inject constructor(
    private val imageLoader: ImageLoader,
    private val context: Context,
    private val imageRequest: CoilRequest
) : BitmapRepository {

    override suspend fun getBitmapDownload(request: ImageRequest): Bitmap? =
        (imageLoader.execute(request).drawable as BitmapDrawable).bitmap

    override suspend fun saveBitmapToStorage(bitmap: Bitmap, imageName: String) {
        val pathDir = getPathDir()
        val fileImage = File(pathDir)
        if(!fileImage.exists()) {
            fileImage.mkdir();
        }

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
            fileImage.deleteRecursively()
        }
    }

    override suspend fun imageDownloadSave(articleList: List<Article>) =
        articleList.map { it.toCoil() }.forEach { article ->
            val request = article.urlToImage?.let { imageRequest.getImageRequest(it) }
            val bitmap = request?.let { getBitmapDownload(request) }
            bitmap?.let {
                article.imageName?.let { imageName -> saveBitmapToStorage(it, imageName) }
            }
        }

    private fun getPathDir() =
//        context.cacheDir.absolutePath.toString() + File.separator + "Images"
        context.filesDir.absolutePath.toString() + File.separator + "Images"

}