package com.lerie_valerie.newsfeed.domain.usecase

import com.lerie_valerie.newsfeed.domain.repository.EventRepository
import javax.inject.Inject

class SendDatabaseClearUseCase @Inject constructor(private val eventRepository: EventRepository) {

    suspend operator fun invoke() =
        eventRepository.sendEvent(EventRepository.Event.ClearDatabase)
}