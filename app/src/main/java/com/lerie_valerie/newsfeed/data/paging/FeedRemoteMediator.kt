package com.lerie_valerie.newsfeed.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.lerie_valerie.newsfeed.Constant.Companion.C_ApiKey
import com.lerie_valerie.newsfeed.Constant.Companion.C_From
import com.lerie_valerie.newsfeed.Constant.Companion.C_Query
import com.lerie_valerie.newsfeed.Constant.Companion.C_Sort
import com.lerie_valerie.newsfeed.data.local.converter.toModel
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.data.local.model.KeyModel
import com.lerie_valerie.newsfeed.data.local.converter.toEntity
import com.lerie_valerie.newsfeed.data.local.usecase.GetKeyListUseCase
import com.lerie_valerie.newsfeed.data.local.usecase.InsertArticleListUseCase
import com.lerie_valerie.newsfeed.data.local.usecase.InsertKeyUseCase
import com.lerie_valerie.newsfeed.data.remote.retrofit.NetInterface
import com.lerie_valerie.newsfeed.domain.usecase.DownloadSaveImageUseCase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val C_Default_Page = 1

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator @Inject constructor(
    private val api: NetInterface,
    private val downloadSaveImage: DownloadSaveImageUseCase,
    private val insertKey: InsertKeyUseCase,
    private val insertArticleList: InsertArticleListUseCase,
    private val getKeyList: GetKeyListUseCase
) : RemoteMediator<Int, ArticleModel>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, ArticleModel>): MediatorResult {

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

//                    downloadSaveImage(articleModelList.map { it.toEntity() })

                    insertKey(KeyModel(page, prevKey, nextKey))
                    insertArticleList(articleModelList)
                }

                MediatorResult.Success(endOfPaginationReached = isEndOfPaginationReached)
            }
            else {
                MediatorResult.Success(true)
            }
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getKey() =
        getKeyList().firstOrNull()


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
                val a = getRemoteKeyForLastItem(state)
                println("aaa ${a}")
                val b = getRemoteKeyForFirstItem(state)
                println("bbb ${b}")
                val b1 = getRemoteKeyForFirstItem2(state)
                println("bbb1 ${b1}")
                keyModel?.next
            }
        }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleModel>): Int? {
        return state.anchorPosition
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
//        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {  }
//            ?.let { repo ->
//                // Get the remote keys of the last item retrieved
//
//                repoDatabase.remoteKeysDao().remoteKeysRepoId(repo.id)
//            }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, ArticleModel>): Int? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo -> repo.id

            }
    }

    private fun getRemoteKeyForFirstItem2(state: PagingState<Int, ArticleModel>): Int? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo -> repo.key

            }
    }
}