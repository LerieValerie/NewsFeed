package com.lerie_valerie.newsfeed.data.local.usecase

import com.lerie_valerie.newsfeed.data.local.model.KeyModel
import com.lerie_valerie.newsfeed.data.local.repository.KeyPagingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertKeyUseCase @Inject constructor(
    private val keyPagingRepository: KeyPagingRepository,
    private val dispatcher: CoroutineDispatcher
){
    suspend operator fun invoke(key: KeyModel) =
        withContext(dispatcher) {
            keyPagingRepository.insertKey(key)
        }
}