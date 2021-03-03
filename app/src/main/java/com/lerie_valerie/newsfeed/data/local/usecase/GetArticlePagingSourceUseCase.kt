package com.lerie_valerie.newsfeed.data.local.usecase

import com.lerie_valerie.newsfeed.data.local.repository.ArticlePagingRepository
import javax.inject.Inject

class GetArticlePagingSourceUseCase @Inject constructor(
    private val articlePagingRepository: ArticlePagingRepository
){
    operator fun invoke() =
        articlePagingRepository.getArticle()
}