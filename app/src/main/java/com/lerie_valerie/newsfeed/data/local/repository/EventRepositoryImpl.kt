package com.lerie_valerie.newsfeed.data.local.repository

import com.lerie_valerie.newsfeed.domain.repository.EventRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor() : EventRepository {
    private val eventChannel =
        MutableSharedFlow<EventRepository.Event>(extraBufferCapacity = DEFAULT_BUFFER_SIZE)

    override val eventFlow
        get() = eventChannel

    override suspend fun sendEvent(e: EventRepository.Event) {
        eventChannel.emit(e)
    }
}