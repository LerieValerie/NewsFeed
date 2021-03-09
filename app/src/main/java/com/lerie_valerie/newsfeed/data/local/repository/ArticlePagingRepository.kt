package com.lerie_valerie.newsfeed.data.local.repository

import androidx.paging.PagingSource
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel

interface ArticlePagingRepository {
    suspend fun insertArticleList(articleList: List<ArticleModel>)
    fun getArticle(): PagingSource<Int, ArticleModel>
}