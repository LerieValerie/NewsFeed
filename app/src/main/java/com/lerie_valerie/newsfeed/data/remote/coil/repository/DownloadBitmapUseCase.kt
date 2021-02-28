package com.lerie_valerie.newsfeed.data.remote.coil.repository

import coil.ImageLoader
import coil.request.ImageRequest
import javax.inject.Inject

class DownloadBitmapUseCase @Inject constructor(private val bitmapRepository: BitmapRepository){
     suspend operator fun invoke(request: ImageRequest) = bitmapRepository.getBitmapDownload(request)
}