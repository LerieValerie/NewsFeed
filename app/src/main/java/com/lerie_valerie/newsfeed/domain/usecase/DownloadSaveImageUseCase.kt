package com.lerie_valerie.newsfeed.domain.usecase

import coil.request.ImageRequest
import com.lerie_valerie.newsfeed.domain.entity.Article
import com.lerie_valerie.newsfeed.domain.repository.BitmapRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DownloadSaveImageUseCase @Inject constructor(
    private val bitmapRepository: BitmapRepository,
    private val dispatcher: CoroutineDispatcher
){
    suspend operator fun invoke(articleList : List<Article>) =
        withContext(dispatcher) {
            bitmapRepository.imageDownloadSave(articleList)
        }
}