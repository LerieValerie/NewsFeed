package com.lerie_valerie.newsfeed.domain.repository

interface KeyRepository {
    suspend fun deleteAllKey()
}