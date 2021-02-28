package com.lerie_valerie.newsfeed.di

import android.content.Context
import com.lerie_valerie.newsfeed.data.paging.repository.ArticleFromRemoteToLocalRepositoryImpl
import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.data.remote.coil.CoilBuilder
import com.lerie_valerie.newsfeed.data.remote.coil.ImageLoader
import com.lerie_valerie.newsfeed.data.remote.coil.repository.*
import com.lerie_valerie.newsfeed.data.remote.retrofit.NetInterface
import com.lerie_valerie.newsfeed.data.remote.retrofit.RetrofitBuilder
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

        @Singleton
        @Provides
        fun provideCoilBuilder(@ApplicationContext appContext: Context) =
            CoilBuilder.build(appContext)

        @Singleton
        @Provides
        fun provideCoilImageLoader(@ApplicationContext appContext: Context) =
            ImageLoader.build(appContext)

//        @Singleton
//        @Provides
//        fun provideBitmapSaveRepository(@ApplicationContext appContext: Context) : BitmapSaveRepository =
//            BitmapSaveRepositoryImpl(appContext)
    }

    @Binds
    abstract fun provideArticleFromRemoteToLocalRepository(
            repositoryImpl: ArticleFromRemoteToLocalRepositoryImpl
    ): ArticleFromRemoteToLocalRepository

    @Binds
    abstract fun provideBitmapDownloadRepository(
            repositoryImpl: BitmapDownloadRepositoryImpl
    ): BitmapDownloadRepository

    @Binds
    abstract fun provideBitmapSaveRepository(
            repositoryImpl: BitmapSaveRepositoryImpl
    ): BitmapSaveRepository

    @Binds
    abstract fun provideApplicationContext(
        @ApplicationContext appContext: Context
    ): Context

    @Binds
    abstract fun provideBitmapRepository(
        repositoryImpl: BitmapRepositoryImpl
    ): BitmapRepository
}
