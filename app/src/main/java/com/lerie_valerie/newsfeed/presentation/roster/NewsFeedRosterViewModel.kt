package com.lerie_valerie.newsfeed.presentation.roster

import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.*
import androidx.paging.cachedIn
import coil.request.ImageRequest
import com.lerie_valerie.newsfeed.domain.usecase.GetBitmapFromStorageUseCase
import com.lerie_valerie.newsfeed.domain.usecase.GetPagingArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

//private const val C_State_Article = "article"

@HiltViewModel
class NewsFeedRosterViewModel @Inject constructor(
    private val loadArticleRemote: GetPagingArticleUseCase,
    private val getBitmapFromStorage: GetBitmapFromStorageUseCase
) : ViewModel() {

    fun loadArticle() =
//        viewModelScope.launch {
            loadArticleRemote().cachedIn(viewModelScope)
//        }

    fun loadArticleLiveData() =
        loadArticleRemote().cachedIn(viewModelScope).asLiveData()

    fun getImageFromStorage(imageName: String?) = getBitmapFromStorage(imageName)




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

