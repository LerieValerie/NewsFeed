package com.lerie_valerie.newsfeed.domain.usecase

import com.lerie_valerie.newsfeed.domain.repository.ArticleFromRemoteToLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPagingArticleUseCase @Inject constructor(
        private val articleFromRemoteToLocalRepository: ArticleFromRemoteToLocalRepository
) {
    operator fun invoke() =
                articleFromRemoteToLocalRepository.getArticle().flowOn(Dispatchers.IO)
}