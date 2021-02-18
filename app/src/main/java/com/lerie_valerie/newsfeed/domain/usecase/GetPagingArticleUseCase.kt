package com.lerie_valerie.newsfeed.domain.usecase

import com.lerie_valerie.newsfeed.domain.repository.ArticleFromRemoteToLocalRepository
import kotlinx.coroutines.withContext

class GetPagingArticleUseCase(
        private val articleFromRemoteToLocalRepository: ArticleFromRemoteToLocalRepository
) {
    operator fun invoke() =
                articleFromRemoteToLocalRepository.getArticle()
}