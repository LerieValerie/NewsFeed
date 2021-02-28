package com.lerie_valerie.newsfeed.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.data.local.converter.toModel
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.data.local.model.KeyModel
import com.lerie_valerie.newsfeed.data.remote.coil.CoilRequest
import com.lerie_valerie.newsfeed.data.remote.coil.repository.DownloadBitmapUseCase
import com.lerie_valerie.newsfeed.data.remote.coil.repository.SaveBitmapUseCase
import com.lerie_valerie.newsfeed.data.remote.retrofit.NetInterface
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val C_Default_Page = 1

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator @Inject constructor(
    private val api: NetInterface,
    private val db: NewsFeedDatabase,
    private val imageRequest: CoilRequest,
    private val downloadBitmap: DownloadBitmapUseCase,
    private val saveBitmap: SaveBitmapUseCase
) : RemoteMediator<Int, ArticleModel>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, ArticleModel>): MediatorResult {

        return try {

            val page = getKeyPage(loadType, state)

            if (page != null) {
                val response = api.getNewsFeed(
                    q = "android",
//                    q = C_Query,
                    from = "2019-04-00",
//                    from = C_From,
                    sortBy = "publi shedAt",
//                    sortBy = C_Sort,
//                    apiKey = "26eddb253e7840f988aec61f2ece2907",
                    apiKey = "2bb5f673d126444da2ed6b70a0fc5b1d",
//                    apiKey = C_ApiKey,
                    page = page,
//                    pageSize = when (loadType) {
//                        LoadType.REFRESH -> state.config.initialLoadSize
//                        else -> state.config.pageSize}
                    pageSize = state.config.pageSize


                )

                val isEndOfPaginationReached = response.articleResponseList.isEmpty()

//                if (loadType == LoadType.REFRESH) {
//                    if (page == 1) {
//                        db.keyDao().deleteAllKey()
//                        db.articleDao().deleteAllArticle()
//                    }
//                }

                if ((loadType == LoadType.REFRESH && page == C_Default_Page) || loadType != LoadType.REFRESH) {
                    val prevKey = if (page == C_Default_Page) null else page.minus(1)
                    val nextKey = if (isEndOfPaginationReached) null else page.plus(1)

                    val articleModelList = response.toArticleList(page).map { it.toModel() }

                    db.keyDao().insertKey(KeyModel(page, prevKey, nextKey))
                    db.articleDao().insertArticleList(articleModelList)

                    imageDownloadSave(articleModelList)

//                    val a = articleModelList.map { it -> it.urlToImage?.let {
//                            url -> imageRequest.getImageRequest(url)
//                    }
//                    }
//                    with(articleModelList) { it -> imageRequest.getImageRequest(it) }
//                    imageRequest.getImageRequest()
//                    downloadBitmap()
                }

                MediatorResult.Success(endOfPaginationReached = isEndOfPaginationReached)
            }
            else {
                MediatorResult.Success(true)
            }
//            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyLastItem(state: PagingState<Int, ArticleModel>) =
        state.lastItemOrNull()?.let { article ->
            db.withTransaction { db.keyDao().getKeyById(article.key)}
        }

    private suspend fun getKeyClosestToCurrentItem (state: PagingState<Int, ArticleModel>)=
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.key?.let { key ->
                db.withTransaction { db.keyDao().getKeyById(key) }
            }
        }

    private suspend fun getKeyFirstItem(state: PagingState<Int, ArticleModel>) =
        state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { article ->
            db.keyDao().getKeyById(article.key)
        }

    private suspend fun getKey() =
        db.keyDao().getKeyList().firstOrNull()


    private suspend fun getKeyPage(loadType: LoadType, state: PagingState<Int, ArticleModel>) =
        when (loadType) {
            LoadType.REFRESH -> {
//                val keyModel = getKeyClosestToCurrentItem(state)
//
//                println("${keyModel?.id} keyClosest")
//
//                val keyModel1 = getKeyLastItem(state)
//
//                println("${keyModel1?.id} keyLast")
//                println("${state.pages.lastIndex} pages")

                val keyModel = getKey()
//                if (keyModel != null) {
//                    null
//                }
//                else
//                    C_Default_Page

                keyModel?.id ?: C_Default_Page
//                keyModel?.next?.minus(1) ?: C_Default_Page
//                C_Default_Page
            }
            LoadType.PREPEND -> {
                null
//                val keyModel = getKeyFirstItem(state)
//                keyModel?.prev
            }
            LoadType.APPEND -> {
                val keyModel = getKey()
//                val keyModel = getKeyLastItem(state)
                keyModel?.next

            }
        }

//    return when (loadType) {
//        LoadType.REFRESH -> {
//            val remoteKeys = getClosestRemoteKey(state)
//            remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
//        }
//        LoadType.APPEND -> {
//            val remoteKeys = getLastRemoteKey(state)
//                ?: throw InvalidObjectException("Remote key should not be null for $loadType")
//            remoteKeys.nextKey
//        }
//        LoadType.PREPEND -> {
//            val remoteKeys = getFirstRemoteKey(state)
//                ?: throw InvalidObjectException("Invalid state, key should not be null")
//            //end of list condition reached
//            remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
//            remoteKeys.prevKey
//        }
//    }

    private suspend fun imageDownloadSave(articleList : List<ArticleModel>) {
        for (article in articleList) {
            val request = article.urlToImage?.let { imageRequest.getImageRequest(it) }
            val bitmap = request?.let { downloadBitmap(request) }
            bitmap?.let {
                val imageName = article.urlToImage?.let {
                    url -> url.substring(url.lastIndexOf('/') + 1, url.length)
                }
                saveBitmap(it, imageName)
            }
        }
    }
}