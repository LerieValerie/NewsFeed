package com.lerie_valerie.newsfeed.data.local.repository

import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.data.local.model.KeyModel
import javax.inject.Inject

class KeyPagingRepositoryImpl @Inject constructor(private val dbInstance: NewsFeedDatabase) :
    KeyPagingRepository {
    override suspend fun insertKey(key: KeyModel) =
        dbInstance.keyDao().insertKey(key)

    override suspend fun getKeyList(): List<KeyModel> =
        dbInstance.keyDao().getKeyList()
}