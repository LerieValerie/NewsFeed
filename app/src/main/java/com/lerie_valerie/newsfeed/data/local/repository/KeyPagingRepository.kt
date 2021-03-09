package com.lerie_valerie.newsfeed.data.local.repository

import com.lerie_valerie.newsfeed.data.local.model.KeyModel

interface KeyPagingRepository {
    suspend fun insertKey(key: KeyModel)
    suspend fun getKeyList(): List<KeyModel>
}