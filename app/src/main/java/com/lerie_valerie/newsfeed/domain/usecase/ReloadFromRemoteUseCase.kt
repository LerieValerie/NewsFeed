package com.lerie_valerie.newsfeed.domain.usecase

class ReloadFromRemoteUseCase (
    private val deleteBitmapFolder: DeleteBitmapFolderUseCase,
    private val getPagingArticle: GetPagingArticleUseCase
) {
    suspend operator fun invoke() {
//        deleteBitmapFolder()
//        getPagingArticle()
    }
}