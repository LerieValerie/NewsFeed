package com.lerie_valerie.newsfeed.domain.usecase

import android.graphics.Bitmap
import com.lerie_valerie.newsfeed.domain.repository.BitmapRepository
import javax.inject.Inject

class SaveBitmapUseCase @Inject constructor(private val bitmapRepository: BitmapRepository) {

    operator fun invoke(bitmap: Bitmap, imageName: String) = bitmapRepository.saveBitmapToStorage(bitmap, imageName)
}