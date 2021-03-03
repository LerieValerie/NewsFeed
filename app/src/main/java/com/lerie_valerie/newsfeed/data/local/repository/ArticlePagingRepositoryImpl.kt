package com.lerie_valerie.newsfeed.data.local.repository

import androidx.paging.PagingSource
import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import javax.inject.Inject

class ArticlePagingRepositoryImpl @Inject constructor(private val dbInstance: NewsFeedDatabase) : ArticlePagingRepository {
    override suspend fun insertArticleList(articleList: List<ArticleModel>) =
        dbInstance.articleDao().insertArticleList(articleList)

    override fun getArticle(): PagingSource<Int, ArticleModel> =
        dbInstance.articleDao().getArticle()

    override suspend fun getArticleByKey(key: Int): List<ArticleModel> =
        dbInstance.articleDao().getArticleByKey(key)
}