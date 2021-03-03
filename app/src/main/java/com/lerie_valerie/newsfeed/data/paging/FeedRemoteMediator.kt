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
import com.lerie_valerie.newsfeed.data.local.converter.toEntity
import com.lerie_valerie.newsfeed.data.local.usecase.GetKeyListUseCase
import com.lerie_valerie.newsfeed.data.local.usecase.InsertArticleListUseCase
import com.lerie_valerie.newsfeed.data.local.usecase.InsertKeyUseCase
import com.lerie_valerie.newsfeed.data.remote.retrofit.NetInterface
import com.lerie_valerie.newsfeed.domain.usecase.DownloadBitmapUseCase
import com.lerie_valerie.newsfeed.domain.usecase.DownloadSaveImageUseCase
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
    private val downloadSaveImage: DownloadSaveImageUseCase,
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

                    downloadSaveImage(articleModelList.map { it.toEntity() })
//                    imageDownloadSave(articleModelList)

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
                keyModel?.next
            }
        }

//    private suspend fun imageDownloadSave(articleList : List<ArticleModel>) {
//        for (article in articleList) {
//            val request = article.urlToImage?.let { imageRequest.getImageRequest(it) }
//            val bitmap = request?.let { downloadBitmap(request) }
//            bitmap?.let {
//                article.imageName?.let { imageName -> saveBitmap(it, imageName) }
//            }
//        }
//    }
}