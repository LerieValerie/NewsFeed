package com.lerie_valerie.newsfeed.domain.usecase

import com.lerie_valerie.newsfeed.domain.repository.ArticleFromRemoteToLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPagingArticleUseCase @Inject constructor(
    private val articleFromRemoteToLocalRepository: ArticleFromRemoteToLocalRepository,
    private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke() =
        articleFromRemoteToLocalRepository.getArticle().flowOn(dispatcher)
}