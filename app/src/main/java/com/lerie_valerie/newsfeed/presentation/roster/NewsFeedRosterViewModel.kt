package com.lerie_valerie.newsfeed.presentation.roster

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lerie_valerie.newsfeed.domain.usecase.GetPagingArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

//private const val C_State_Article = "article"

@HiltViewModel
class NewsFeedRosterViewModel @Inject constructor(
    private val loadArticleRemote: GetPagingArticleUseCase
) : ViewModel() {

    fun loadArticle() =
//        viewModelScope.launch {
            loadArticleRemote().cachedIn(viewModelScope)
//        }

    fun loadArticleLiveData() =
        loadArticleRemote().cachedIn(viewModelScope).asLiveData()


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

