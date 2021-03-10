package com.lerie_valerie.newsfeed.domain.usecase

import com.lerie_valerie.newsfeed.domain.repository.EventRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetEventFlowUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke() = eventRepository.eventFlow.flowOn(dispatcher)
}