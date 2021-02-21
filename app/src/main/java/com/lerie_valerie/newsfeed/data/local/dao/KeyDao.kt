package com.lerie_valerie.newsfeed.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lerie_valerie.newsfeed.data.local.model.KeyModel

@Dao
interface KeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeyList(keyList: List<KeyModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: KeyModel)

//    @Query("SELECT * FROM remote_key WHERE id = :articleId")
//    fun getKeyByArticleId(articleId: Int): KeyModel?

    @Query("SELECT * FROM remote_key ORDER BY id DESC")
    suspend fun getKeyList(): List<KeyModel>

    @Query("SELECT * FROM remote_key WHERE id = :id")
    suspend fun getKeyById(id: Int): KeyModel?

    @Query("DELETE FROM remote_key")
    suspend fun deleteAllKey()
}