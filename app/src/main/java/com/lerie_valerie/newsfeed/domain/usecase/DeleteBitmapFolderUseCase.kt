package com.lerie_valerie.newsfeed.domain.usecase

import android.graphics.Bitmap
import com.lerie_valerie.newsfeed.domain.repository.BitmapRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteBitmapFolderUseCase @Inject constructor(
    private val bitmapRepository: BitmapRepository,
    private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke() =
//        withContext(dispatcher) {
            bitmapRepository.deleteBitmapFolder()
//        }
}