package com.lerie_valerie.newsfeed.domain.usecase

import com.lerie_valerie.newsfeed.domain.repository.BitmapRepository
import javax.inject.Inject

class GetBitmapFromStorageUseCase @Inject constructor(
    private val bitmapRepository: BitmapRepository
) {
    operator fun invoke(imageName: String?) =
        bitmapRepository.getBitmapFromStorage(imageName)
}