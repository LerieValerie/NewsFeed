package com.lerie_valerie.newsfeed.domain.usecase

import coil.request.ImageRequest
import com.lerie_valerie.newsfeed.domain.repository.BitmapRepository
import javax.inject.Inject

class DownloadBitmapUseCase @Inject constructor(private val bitmapRepository: BitmapRepository){
     suspend operator fun invoke(request: ImageRequest) = bitmapRepository.getBitmapDownload(request)
}