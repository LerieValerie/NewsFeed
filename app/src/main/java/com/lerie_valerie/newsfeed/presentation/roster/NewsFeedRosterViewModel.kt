package com.lerie_valerie.newsfeed.presentation.roster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lerie_valerie.newsfeed.domain.usecase.GetPagingArticleUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsFeedRosterViewModel(
    private val loadArticleRemote: GetPagingArticleUseCase
) : ViewModel() {

    fun loadArticle() =
//        viewModelScope.launch {
            loadArticleRemote().cachedIn(viewModelScope)
//        }


//    fun loadSpecialityList() = loadSpecialityListById(workerId).map {
//        it.map { speciality ->
//            speciality.toView()
//        }
//    }.asLiveData()
}

