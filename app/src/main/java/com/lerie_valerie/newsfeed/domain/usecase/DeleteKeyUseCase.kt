package com.lerie_valerie.newsfeed.domain.usecase

import com.lerie_valerie.newsfeed.domain.repository.KeyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteKeyUseCase @Inject constructor(
    private val keyRepository: KeyRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() =
        withContext(dispatcher) {
            keyRepository.deleteAllKey()
        }
}