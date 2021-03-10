package com.lerie_valerie.newsfeed.domain.usecase

import com.lerie_valerie.newsfeed.domain.repository.EventRepository
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetEventFlowUseCase @Inject constructor(private val eventRepository: EventRepository) {
    operator fun invoke() = eventRepository.eventFlow
}