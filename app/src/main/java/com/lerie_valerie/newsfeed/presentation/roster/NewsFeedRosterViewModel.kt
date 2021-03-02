package com.lerie_valerie.newsfeed.presentation.roster

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import coil.request.ImageRequest
import com.lerie_valerie.newsfeed.domain.usecase.DeleteBitmapFolderUseCase
import com.lerie_valerie.newsfeed.domain.usecase.GetBitmapFromStorageUseCase
import com.lerie_valerie.newsfeed.domain.usecase.GetPagingArticleUseCase
import com.lerie_valerie.newsfeed.presentation.view.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

//private const val C_State_Article = "article"

@HiltViewModel
class NewsFeedRosterViewModel @Inject constructor(
    private val loadArticleRemote: GetPagingArticleUseCase,
    private val getBitmapFromStorage: GetBitmapFromStorageUseCase,
    private val deleteBitmapFolder: DeleteBitmapFolderUseCase
) : ViewModel() {

    fun loadArticle() =
//        viewModelScope.launch {
            loadArticleRemote().map {
                it.map {
                        article -> article.toView()
                }
            }.cachedIn(viewModelScope)
//        }

    fun loadArticleLiveData() =
        loadArticleRemote().cachedIn(viewModelScope).asLiveData()

    fun getImageFromStorage(imageName: String?) =
        getBitmapFromStorage(imageName)


    fun deleteBitmapFromStorage() =
            deleteBitmapFolder()




//    fun refresh() {
//        numbers = buildItems()
//        state.set(STATE_NUMBERS, numbers)
//    }


//    fun loadSpecialityList() = loadSpecialityListById(workerId).map {
//        it.map { speciality ->
//            speciality.toView()
//        }
//    }.asLiveData()
}

