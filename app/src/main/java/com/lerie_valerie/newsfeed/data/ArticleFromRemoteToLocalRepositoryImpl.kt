package com.lerie_valerie.newsfeed.data

import androidx.paging.*
import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.data.local.converter.toEntity
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.data.remote.NetInterface
import com.lerie_valerie.newsfeed.domain.entity.Article
import com.lerie_valerie.newsfeed.domain.repository.ArticleFromRemoteToLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ArticleFromRemoteToLocalRepositoryImpl(
//        private val api: NetInterface,
        private val db: NewsFeedDatabase,
        private val remoteMediator: FeedRemoteMediator
) : ArticleFromRemoteToLocalRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticle(): Flow<PagingData<Article>> {
        val pagingArticleModel = Pager(
                PagingConfig(pageSize = 20, enablePlaceholders = false, prefetchDistance = 5),
                remoteMediator = remoteMediator,
                pagingSourceFactory = { db.articleDao().getArticle() }
        ).flow

        return pagingArticleModel.map { it -> it.map { it.toEntity() } }
//        return flowOf()
    }
}