package com.lerie_valerie.newsfeed.domain.usecase

import com.lerie_valerie.newsfeed.domain.repository.EventRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class SendDatabaseClearUseCase @Inject constructor(private val eventRepository: EventRepository) {

    suspend operator fun invoke() {
//        (eventRepository.eventFlow as MutableSharedFlow).emit(EventRepository.Event.ClearDatabase())

        eventRepository.sendEvent(EventRepository.Event.ClearDatabase())
    }
}