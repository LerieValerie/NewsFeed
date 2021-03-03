package com.lerie_valerie.newsfeed.data.local.usecase

import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.data.local.repository.ArticlePagingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertArticleListUseCase @Inject constructor(
    private val articlePagingRepository: ArticlePagingRepository,
    private val dispatcher: CoroutineDispatcher
){
    suspend operator fun invoke(articleList: List<ArticleModel>) =
        withContext(dispatcher) {
            articlePagingRepository.insertArticleList(articleList)
        }
}