package com.lerie_valerie.newsfeed.domain.repository

interface ArticleRepository {
    suspend fun deleteAllArticle()
}