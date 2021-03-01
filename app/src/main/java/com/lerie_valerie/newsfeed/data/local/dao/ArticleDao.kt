package com.lerie_valerie.newsfeed.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel

@Dao
interface ArticleDao {
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleList(articleList: List<ArticleModel>)

//    @Query("SELECT * FROM article ORDER BY id")
//    fun getArticle(): PagingSource<Int, ArticleModel>
//    @Query("SELECT * FROM article ORDER BY `key`, id")
//    fun getArticle(): PagingSource<Int, ArticleModel>
    @Query("SELECT * FROM article ORDER BY `key`, id")
    fun getArticle(): PagingSource<Int, ArticleModel>

    @Query("SELECT * FROM article WHERE `key` = :key ORDER BY id")
    suspend fun getArticleByKey(key: Int): List<ArticleModel>

//    @Query("SELECT * FROM article ORDER BY date DESC")
//    fun getArticleById(): PagingSource<Int, ArticleModel>
////    fun getArticle(): List<ArticleModel>

    @Query("DELETE FROM article")
    suspend fun deleteAllArticle()
}