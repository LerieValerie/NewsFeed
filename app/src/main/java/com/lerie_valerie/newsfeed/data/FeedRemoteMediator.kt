package com.lerie_valerie.newsfeed.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lerie_valerie.newsfeed.Constant.Companion.C_ApiKey
import com.lerie_valerie.newsfeed.Constant.Companion.C_From
import com.lerie_valerie.newsfeed.Constant.Companion.C_Query
import com.lerie_valerie.newsfeed.Constant.Companion.C_Sort
import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.data.local.converter.toModel
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.data.local.model.KeyModel
import com.lerie_valerie.newsfeed.data.remote.NetInterface
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator(
        private val api: NetInterface,
        private val db: NewsFeedDatabase
) : RemoteMediator<Int, ArticleModel>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, ArticleModel>): MediatorResult {

        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val key = getRemoteKeyClosestToCurrentPosition(state)
                    key?.next?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val key = getRemoteKeyForLastItem(state)
//                        ?: throw InvalidObjectException("Result is empty")
                    key?.next ?: return MediatorResult.Success(true)
                }
            }

            val response = api.getNewsFeed(
                    q = C_Query,
                    from = C_From,
                    sortBy = C_Sort,
                    apiKey = C_ApiKey,
                    page = page,
                    pageSize = state.config.pageSize
            )

            val endOfPaginationReached = response.articleResponseList.size < state.config.pageSize

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.keyDao().deleteAllKey()
                    db.articleDao().deleteAllArticle()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val articleModelList = response.toArticleList().map { it.toModel() }
                val keyList = articleModelList.map {
                    KeyModel(it.id, prevKey, nextKey)
                }
                db.keyDao().insertKeyList(keyList)
                db.articleDao().insertArticleList(articleModelList)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleModel>): KeyModel? {
        return state.lastItemOrNull()?.let { article ->
            db.withTransaction { db.keyDao().getKeyByArticleId(articleId = article.id) }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ArticleModel>): KeyModel? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.withTransaction { db.keyDao().getKeyByArticleId(id) }
            }
        }
    }
}