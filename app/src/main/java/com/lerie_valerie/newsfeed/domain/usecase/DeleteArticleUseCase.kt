package com.lerie_valerie.newsfeed.domain.usecase

import com.lerie_valerie.newsfeed.domain.repository.ArticleRepository
import com.lerie_valerie.newsfeed.domain.repository.BitmapRepository
import com.lerie_valerie.newsfeed.domain.repository.KeyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() =
        withContext(dispatcher) {
            articleRepository.deleteAllArticle()
        }
}