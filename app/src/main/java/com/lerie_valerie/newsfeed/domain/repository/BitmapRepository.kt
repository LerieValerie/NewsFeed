package com.lerie_valerie.newsfeed.domain.repository

import android.graphics.Bitmap
import coil.request.ImageRequest
import com.lerie_valerie.newsfeed.data.coil.CoilRequest
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.domain.entity.Article

interface BitmapRepository {
    fun getBitmapFromStorage(imageName: String?) : Bitmap?
    fun deleteBitmapFolder()
    suspend fun imageDownloadSave(articleList : List<Article>)
}