package com.lerie_valerie.newsfeed.di

import android.content.Context
import com.lerie_valerie.newsfeed.data.paging.repository.ArticleFromRemoteToLocalRepositoryImpl
import com.lerie_valerie.newsfeed.data.local.NewsFeedDatabase
import com.lerie_valerie.newsfeed.data.coil.CoilBuilder
import com.lerie_valerie.newsfeed.data.coil.ImageLoader
import com.lerie_valerie.newsfeed.data.coil.repository.*
import com.lerie_valerie.newsfeed.data.local.repository.*
import com.lerie_valerie.newsfeed.data.remote.retrofit.NetInterface
import com.lerie_valerie.newsfeed.data.remote.retrofit.RetrofitBuilder
import com.lerie_valerie.newsfeed.domain.repository.*
import dagger.Binds
import dagger.BindsInstance
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
                RetrofitBuilder.buildApi()

//        @Singleton
//        @Provides
//        fun provideCoilBuilder(@ApplicationContext appContext: Context) =
//            CoilBuilder.build(appContext)
//
//        @Singleton
//        @Provides
//        fun provideCoilImageLoader(@ApplicationContext appContext: Context) =
//            ImageLoader.build(appContext)

//        @Singleton
//        @Provides
//        fun provideEventRepository() : EventRepository = EventRepositoryImpl()
    }

    @Singleton
    @Binds
    abstract fun provideArticleFromRemoteToLocalRepository(
            repositoryImpl: ArticleFromRemoteToLocalRepositoryImpl
    ): ArticleFromRemoteToLocalRepository

    @Singleton
    @Binds
    abstract fun provideApplicationContext(
        @ApplicationContext appContext: Context
    ): Context

//    @Singleton
//    @Binds
//    abstract fun provideBitmapRepository(
//        repositoryImpl: BitmapRepositoryImpl
//    ): BitmapRepository

    @Singleton
    @Binds
    abstract fun provideArticleRepository(
        repositoryImpl: ArticleRepositoryImpl
    ): ArticleRepository

    @Singleton
    @Binds
    abstract fun provideKeyRepository(
        repositoryImpl: KeyRepositoryImpl
    ): KeyRepository

    @Singleton
    @Binds
    abstract fun provideArticlePagingRepository(
        repositoryImpl: ArticlePagingRepositoryImpl
    ): ArticlePagingRepository

    @Singleton
    @Binds
    abstract fun provideKeyPagingRepository(
        repositoryImpl: KeyPagingRepositoryImpl
    ): KeyPagingRepository

    @Singleton
    @Binds
    abstract fun provideEventRepository(
        repositoryImpl: EventRepositoryImpl
    ): EventRepository


}
