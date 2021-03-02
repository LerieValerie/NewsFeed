package com.lerie_valerie.newsfeed.domain.usecase

import com.lerie_valerie.newsfeed.domain.repository.BitmapRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBitmapFromStorageUseCase @Inject constructor(
    private val bitmapRepository: BitmapRepository,
    private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(imageName: String?) =
//        withContext(dispatcher) {
            bitmapRepository.getBitmapFromStorage(imageName)
//        }

}