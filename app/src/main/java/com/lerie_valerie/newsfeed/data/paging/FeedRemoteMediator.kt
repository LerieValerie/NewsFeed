package com.lerie_valerie.newsfeed.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.lerie_valerie.newsfeed.data.local.converter.toModel
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.data.local.model.KeyModel
import com.lerie_valerie.newsfeed.data.local.repository.ArticlePagingRepository
import com.lerie_valerie.newsfeed.data.local.repository.KeyPagingRepository
import com.lerie_valerie.newsfeed.data.remote.retrofit.NetInterface
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator @Inject constructor(
    private val api: NetInterface,
    private val keyPagingRepository: KeyPagingRepository,
    private val articlePagingRepository: ArticlePagingRepository
) : RemoteMediator<Int, ArticleModel>() {

    companion object {
        private const val C_Default_Page = 1

        private const val C_Query = "android"
        private const val C_ApiKey = "2bb5f673d126444da2ed6b70a0fc5b1d"
//        private const val C_ApiKey = "631dff9582c04fb5b7f234174ad7dbd8"
        private const val C_From = "2019-04-00"
        private const val C_Sort = "publi shedAt"
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleModel>
    ): MediatorResult {
        return try {
            val page = getKeyPage(loadType)

            if (page != null) {
                val response = api.getNewsFeed(
                    q = C_Query,
                    from = C_From,
                    sortBy = C_Sort,
                    apiKey = C_ApiKey,
                    page = page,
                    pageSize = state.config.pageSize
                )

                val isEndOfPaginationReached = response.articleResponseList.isEmpty()

                if ((loadType == LoadType.REFRESH && page == C_Default_Page) || loadType != LoadType.REFRESH) {
                    val prevKey = if (page == C_Default_Page) null else page.minus(1)
                    val nextKey = if (isEndOfPaginationReached) null else page.plus(1)

                    val articleModelList = response.toArticleList(page).map { it.toModel() }

                    keyPagingRepository.insertKey(KeyModel(page, prevKey, nextKey))
                    articlePagingRepository.insertArticleList(articleModelList)
                }

                MediatorResult.Success(endOfPaginationReached = isEndOfPaginationReached)
            } else {
                MediatorResult.Success(true)
            }
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getKey() =
        keyPagingRepository.getKeyList().firstOrNull()

    private suspend fun getKeyPage(loadType: LoadType) =
        when (loadType) {
            LoadType.REFRESH -> {
                val keyModel = getKey()
                keyModel?.id ?: C_Default_Page
            }
            LoadType.PREPEND -> {
                null
            }
            LoadType.APPEND -> {
                val keyModel = getKey()
                keyModel?.next
            }
        }
}