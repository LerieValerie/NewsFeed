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

    @Query("SELECT * FROM remote_key WHERE id = :articleId")
    fun getKeyByArticleId(articleId: Int): KeyModel?

    @Query("DELETE FROM remote_key")
    fun deleteAllKey()
}