package com.lerie_valerie.newsfeed.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lerie_valerie.newsfeed.data.local.dao.ArticleDao
import com.lerie_valerie.newsfeed.data.local.dao.KeyDao
import com.lerie_valerie.newsfeed.data.local.model.ArticleModel
import com.lerie_valerie.newsfeed.data.local.model.KeyModel

private const val DB_NAME = "news_feed.db"

@Database(
        entities = [
            ArticleModel::class,
            KeyModel::class
        ],
        version = 1,
        exportSchema = false
)

abstract class NewsFeedDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun keyDao(): KeyDao

    companion object {
        fun newInstance(context: Context) =
                Room.databaseBuilder(context, NewsFeedDatabase::class.java, DB_NAME).build()
    }
}