package com.lerie_valerie.newsfeed.domain.repository

import kotlinx.coroutines.flow.Flow

interface EventRepository {
    sealed class Event {
        class ClearDatabase(): Event()
    }

    val eventFlow: Flow<Event>

    suspend fun sendEvent(e: Event)
}