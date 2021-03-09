package com.lerie_valerie.newsfeed.data.local.repository

import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val dbInstance: NewsFeedDatabase) :
    ArticleRepository {
    override suspend fun deleteAllArticle() {
        dbInstance.articleDao().deleteAllArticle()
    }
}