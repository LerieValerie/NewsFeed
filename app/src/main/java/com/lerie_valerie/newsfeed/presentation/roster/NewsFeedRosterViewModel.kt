package com.lerie_valerie.newsfeed.presentation.roster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.lerie_valerie.newsfeed.domain.repository.EventRepository
import com.lerie_valerie.newsfeed.domain.usecase.ClearDatabaseUseCase
import com.lerie_valerie.newsfeed.domain.usecase.GetEventFlowUseCase
import com.lerie_valerie.newsfeed.domain.usecase.GetPagingArticleUseCase
import com.lerie_valerie.newsfeed.presentation.view.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsFeedRosterViewModel @Inject constructor(
    private val loadArticleRemote: GetPagingArticleUseCase,
    private val clearDatabase: ClearDatabaseUseCase,
    private val getEventFlow: GetEventFlowUseCase
) : ViewModel() {

    val events: Flow<EventRepository.Event>
        get() = getEventFlow()


    fun loadArticle() =
        loadArticleRemote().map {
            it.map { article ->
                article.toView()
            }
        }.cachedIn(viewModelScope)

    fun clearAll() {
        viewModelScope.launch {
            clearDatabase()
        }
    }
}

