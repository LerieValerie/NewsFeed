package com.lerie_valerie.newsfeed.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class KeyModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val prev: Int?,
    val next: Int?
)