package com.lerie_valerie.newsfeed.data.local.usecase

import com.lerie_valerie.newsfeed.data.local.repository.KeyPagingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetKeyListUseCase @Inject constructor(
    private val keyPagingRepository: KeyPagingRepository,
    private val dispatcher: CoroutineDispatcher
){
    suspend operator fun invoke() =
        withContext(dispatcher) {
            keyPagingRepository.getKeyList()
        }
}