package com.lerie_valerie.newsfeed.data.remote.coil.repository

import android.graphics.Bitmap
import coil.ImageLoader
import javax.inject.Inject

class SaveBitmapUseCase @Inject constructor(private val bitmapRepository: BitmapRepository) {

    operator fun invoke(bitmap: Bitmap, imageName: String) = bitmapRepository.saveBitmapToStorage(bitmap, imageName)
}