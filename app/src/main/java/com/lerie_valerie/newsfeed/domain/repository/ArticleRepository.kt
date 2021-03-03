package com.lerie_valerie.newsfeed.domain.repository

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.domain.entity.Article

interface ArticleRepository {
    suspend fun deleteAllArticle()
}