package com.lerie_valerie.newsfeed.domain.repository

import androidx.paging.PagingData
import com.lerie_valerie.newsfeed.domain.entity.Article
import kotlinx.coroutines.flow.Flow

interface ArticleFromRemoteToLocalRepository {
    fun getArticle(): Flow<PagingData<Article>>
}