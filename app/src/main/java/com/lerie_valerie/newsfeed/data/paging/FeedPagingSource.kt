package com.lerie_valerie.newsfeed.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@ExperimentalPagingApi
class FeedPagingSource @Inject constructor(private val db: NewsFeedDatabase) :
    PagingSource<Int, ArticleModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {

        val page = params.key ?: 1
        return try {
            val articleModelList = db.articleDao().getArticleByKey(page)
            val keyModel = db.keyDao().getKeyById(page)

//            LoadResult.Page(
//                response, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
//                nextKey = if (response.isEmpty()) null else page + 1
//            )

            LoadResult.Page(
                articleModelList ?: listOf(),
                keyModel?.prev,
                keyModel?.next
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? {
        TODO("Not yet implemented")
    }

}