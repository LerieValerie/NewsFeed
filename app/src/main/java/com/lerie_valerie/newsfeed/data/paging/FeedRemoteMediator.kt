package com.lerie_valerie.newsfeed.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.lerie_valerie.newsfeed.Constant.Companion.C_From
import com.lerie_valerie.newsfeed.Constant.Companion.C_Query
import com.lerie_valerie.newsfeed.Constant.Companion.C_Sort
import com.lerie_valerie.newsfeed.data.local.converter.toModel
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.data.local.model.KeyModel
import com.lerie_valerie.newsfeed.data.local.repository.ArticlePagingRepository
import com.lerie_valerie.newsfeed.data.local.repository.KeyPagingRepository
import com.lerie_valerie.newsfeed.data.remote.retrofit.NetInterface
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val C_Default_Page = 1

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator @Inject constructor(
    private val api: NetInterface,
    private val keyPagingRepository: KeyPagingRepository,
    private val articlePagingRepository: ArticlePagingRepository
) : RemoteMediator<Int, ArticleModel>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleModel>
    ): MediatorResult {

        return try {

            val page = getKeyPage(loadType, state)

            if (page != null) {
                val response = api.getNewsFeed(
                    q = C_Query,
                    from = C_From,
                    sortBy = C_Sort,
//                    apiKey = "26eddb253e7840f988aec61f2ece2907",
//                    apiKey = "631dff9582c04fb5b7f234174ad7dbd8",
                    apiKey = "2bb5f673d126444da2ed6b70a0fc5b1d",
                    //        const val C_ApiKey = "2bb5f673d126444da2ed6b70a0fc5b1d"

//                    apiKey = C_ApiKey,
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


    private suspend fun getKeyPage(loadType: LoadType, state: PagingState<Int, ArticleModel>) =
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