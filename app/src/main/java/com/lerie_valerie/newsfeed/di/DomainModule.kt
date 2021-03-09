package com.lerie_valerie.newsfeed.di

import com.lerie_valerie.newsfeed.data.paging.repository.ArticleFromRemoteToLocalRepositoryImpl
import com.lerie_valerie.newsfeed.domain.repository.ArticleFromRemoteToLocalRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    companion object {
        @Singleton
        @Provides
        fun provideCoroutineDispatcher() =
            Dispatchers.Default
    }
}