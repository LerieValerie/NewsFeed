package com.lerie_valerie.newsfeed.data.paging.repository

import androidx.paging.*
import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.data.local.converter.toEntity
import com.lerie_valerie.newsfeed.data.local.usecase.GetArticlePagingSourceUseCase
import com.lerie_valerie.newsfeed.data.paging.FeedRemoteMediator
import com.lerie_valerie.newsfeed.domain.entity.Article
import com.lerie_valerie.newsfeed.domain.repository.ArticleFromRemoteToLocalRepository
import com.lerie_valerie.newsfeed.domain.usecase.GetPagingArticleUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleFromRemoteToLocalRepositoryImpl @Inject constructor(
        private val remoteMediator: FeedRemoteMediator,
        private val getPagingArticle: GetArticlePagingSourceUseCase
) : ArticleFromRemoteToLocalRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticle(): Flow<PagingData<Article>> {

        val pagingArticleModel = Pager(
                PagingConfig(pageSize = 5, enablePlaceholders = true, prefetchDistance = 2),
                remoteMediator = remoteMediator,
                pagingSourceFactory = { getPagingArticle() }
        ).flow

        return pagingArticleModel.map { it -> it.map { it.toEntity() } }
    }
}