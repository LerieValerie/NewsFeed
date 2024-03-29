package com.lerie_valerie.newsfeed.domain.usecase

import com.lerie_valerie.newsfeed.domain.repository.ArticleRepository
import com.lerie_valerie.newsfeed.domain.repository.EventRepository
import com.lerie_valerie.newsfeed.domain.repository.KeyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClearDatabaseUseCase @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val keyRepository: KeyRepository,
    private val eventRepository: EventRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() =
        withContext(dispatcher) {
            articleRepository.deleteAllArticle()
            keyRepository.deleteAllKey()

            eventRepository.sendEvent(EventRepository.Event.ClearDatabase)
        }
}