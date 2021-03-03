package com.lerie_valerie.newsfeed.data.local.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lerie_valerie.newsfeed.data.local.model.KeyModel

interface KeyPagingRepository {
    suspend fun insertKeyList(keyList: List<KeyModel>)
    suspend fun insertKey(key: KeyModel)
    suspend fun getKeyList(): List<KeyModel>
    suspend fun getKeyById(id: Int): KeyModel?
}