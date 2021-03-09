package com.lerie_valerie.newsfeed.presentation.roster

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import com.lerie_valerie.newsfeed.domain.repository.EventRepository
import com.lerie_valerie.newsfeed.domain.usecase.*
import com.lerie_valerie.newsfeed.presentation.view.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
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
                it.map {
                        article -> article.toView()
                }
            }.cachedIn(viewModelScope)

    fun clearAll() {
        viewModelScope.launch {
            clearDatabase()
        }
    }
}

