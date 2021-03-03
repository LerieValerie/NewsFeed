package com.lerie_valerie.newsfeed.data.local.usecase

import com.lerie_valerie.newsfeed.data.local.repository.ArticlePagingRepository
import com.lerie_valerie.newsfeed.data.local.repository.KeyPagingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetKeyByIdUseCase @Inject constructor(
    private val keyPagingRepository: KeyPagingRepository,
    private val dispatcher: CoroutineDispatcher
){
    suspend operator fun invoke(id: Int) =
        withContext(dispatcher) {
            keyPagingRepository.getKeyById(id)
        }
}