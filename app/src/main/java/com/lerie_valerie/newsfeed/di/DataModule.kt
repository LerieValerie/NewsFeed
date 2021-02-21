package com.lerie_valerie.newsfeed.di

import android.content.Context
import com.lerie_valerie.newsfeed.data.paging.ArticleFromRemoteToLocalRepositoryImpl
import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.data.remote.NetInterface
import com.lerie_valerie.newsfeed.data.remote.RetrofitBuilder
import com.lerie_valerie.newsfeed.domain.repository.ArticleFromRemoteToLocalRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext appContext: Context): NewsFeedDatabase =
                NewsFeedDatabase.newInstance(appContext)

        @Singleton
        @Provides
        fun provideNetInterface(): NetInterface =
                RetrofitBuilder.buildApi(RetrofitBuilder.BASE_API_URL)
    }

    @Binds
    abstract fun provideArticleFromRemoteToLocalRepository(
            repositoryImpl: ArticleFromRemoteToLocalRepositoryImpl
    ): ArticleFromRemoteToLocalRepository
}
