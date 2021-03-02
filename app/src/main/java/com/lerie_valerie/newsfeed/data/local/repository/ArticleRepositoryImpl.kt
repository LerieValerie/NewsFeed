package com.lerie_valerie.newsfeed.data.local.repository

import androidx.paging.PagingSource
import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.domain.entity.Article
import com.lerie_valerie.newsfeed.domain.repository.ArticleRepository

class ArticleRepositoryImpl(private val dbInstance: NewsFeedDatabase) : ArticleRepository {
    override suspend fun insertArticleList(articleList: List<Article>) {
        TODO("Not yet implemented")
    }

    override fun getArticle(): PagingSource<Int, Article> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticleByKey(key: Int): List<Article> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllArticle() {
        TODO("Not yet implemented")
    }
}