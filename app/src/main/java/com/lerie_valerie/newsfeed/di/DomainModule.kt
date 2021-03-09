package com.lerie_valerie.newsfeed.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
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