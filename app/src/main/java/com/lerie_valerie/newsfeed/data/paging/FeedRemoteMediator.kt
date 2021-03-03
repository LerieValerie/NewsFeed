package com.lerie_valerie.newsfeed.data.paging

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
import com.lerie_valerie.newsfeed.data.coil.CoilRequest
import com.lerie_valerie.newsfeed.data.local.usecase.GetKeyListUseCase
import com.lerie_valerie.newsfeed.data.local.usecase.InsertArticleListUseCase
import com.lerie_valerie.newsfeed.data.local.usecase.InsertKeyUseCase
import com.lerie_valerie.newsfeed.data.remote.retrofit.NetInterface
import com.lerie_valerie.newsfeed.domain.usecase.DownloadBitmapUseCase
import com.lerie_valerie.newsfeed.domain.usecase.SaveBitmapUseCase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val C_Default_Page = 1

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator @Inject constructor(
    private val api: NetInterface,
    private val imageRequest: CoilRequest,
    private val downloadBitmap: DownloadBitmapUseCase,
    private val saveBitmap: SaveBitmapUseCase,
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
                    apiKey = C_ApiKey,
                    page = page,
                    pageSize = state.config.pageSize
                )

                val isEndOfPaginationReached = response.articleResponseList.isEmpty()

                if ((loadType == LoadType.REFRESH && page == C_Default_Page) || loadType != LoadType.REFRESH) {
                    val prevKey = if (page == C_Default_Page) null else page.minus(1)
                    val nextKey = if (isEndOfPaginationReached) null else page.plus(1)

                    val articleModelList = response.toArticleList(page).map { it.toModel() }

                    imageDownloadSave(articleModelList)

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

//    private suspend fun getKeyLastItem(state: PagingState<Int, ArticleModel>) =
//        state.lastItemOrNull()?.let { article ->
//            db.withTransaction { db.keyDao().getKeyById(article.key)}
//        }
//
//    private suspend fun getKeyClosestToCurrentItem (state: PagingState<Int, ArticleModel>)=
//        state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.key?.let { key ->
//                db.withTransaction { db.keyDao().getKeyById(key) }
//            }
//        }
//
//    private suspend fun getKeyFirstItem(state: PagingState<Int, ArticleModel>) =
//        state.pages.firstOrNull {
//            it.data.isNotEmpty()
//        }?.data?.firstOrNull()?.let { article ->
//            db.keyDao().getKeyById(article.key)
//        }

    private suspend fun getKey() =
        getKeyList().firstOrNull()


    private suspend fun getKeyPage(loadType: LoadType, state: PagingState<Int, ArticleModel>) =
        when (loadType) {
            LoadType.REFRESH -> {
//                val keyModel = getKeyClosestToCurrentItem(state)
////
//                println("${keyModel?.id} keyClosest")
//
//                val keyModel1 = getKeyLastItem(state)
//
//                println("${keyModel1?.id} keyLast")
//                println("${state.pages.lastIndex} pages")

                val keyModel = getKey()
                keyModel?.id ?: C_Default_Page
//                keyModel?.next?.minus(1) ?: C_Default_Page
//                C_Default_Page
            }
            LoadType.PREPEND -> {
                null
            }
            LoadType.APPEND -> {
                val keyModel = getKey()
                keyModel?.next
            }
        }

    private suspend fun imageDownloadSave(articleList : List<ArticleModel>) {
        for (article in articleList) {
            val request = article.urlToImage?.let { imageRequest.getImageRequest(it) }
            val bitmap = request?.let { downloadBitmap(request) }
            bitmap?.let {
                article.imageName?.let { imageName -> saveBitmap(it, imageName) }
            }
        }
    }
}