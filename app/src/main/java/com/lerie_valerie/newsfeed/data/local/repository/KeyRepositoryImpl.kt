package com.lerie_valerie.newsfeed.data.local.repository

import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.domain.repository.ArticleRepository
import com.lerie_valerie.newsfeed.domain.repository.KeyRepository
import javax.inject.Inject

class KeyRepositoryImpl @Inject constructor(private val dbInstance: NewsFeedDatabase) : KeyRepository {
    override suspend fun deleteAllKey() {
        dbInstance.keyDao().deleteAllKey()
    }
}