package com.lerie_valerie.newsfeed.data.local.usecase

import com.lerie_valerie.newsfeed.data.local.repository.ArticlePagingRepository
import com.lerie_valerie.newsfeed.domain.repository.ArticleRepository
import com.lerie_valerie.newsfeed.domain.repository.KeyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetArticleByKeyUseCase @Inject constructor(
    private val articlePagingRepository: ArticlePagingRepository,
    private val dispatcher: CoroutineDispatcher
    ) {
    suspend operator fun invoke(key: Int) =
        withContext(dispatcher) {
            articlePagingRepository.getArticleByKey(key)
        }
}