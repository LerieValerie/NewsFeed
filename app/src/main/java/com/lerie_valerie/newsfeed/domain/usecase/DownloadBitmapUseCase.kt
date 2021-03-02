package com.lerie_valerie.newsfeed.domain.usecase

import coil.request.ImageRequest
import com.lerie_valerie.newsfeed.domain.repository.BitmapRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DownloadBitmapUseCase @Inject constructor(
     private val bitmapRepository: BitmapRepository,
     private val dispatcher: CoroutineDispatcher
){
     suspend operator fun invoke(request: ImageRequest) =
          withContext(dispatcher) {
               bitmapRepository.getBitmapDownload(request)
          }
}