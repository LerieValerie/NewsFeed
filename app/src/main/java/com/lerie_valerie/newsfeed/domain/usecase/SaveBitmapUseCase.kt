package com.lerie_valerie.newsfeed.domain.usecase

import android.graphics.Bitmap
import com.lerie_valerie.newsfeed.domain.repository.BitmapRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveBitmapUseCase @Inject constructor(
    private val bitmapRepository: BitmapRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(bitmap: Bitmap, imageName: String) =
        withContext(dispatcher) {
            bitmapRepository.saveBitmapToStorage(bitmap, imageName)
        }
}