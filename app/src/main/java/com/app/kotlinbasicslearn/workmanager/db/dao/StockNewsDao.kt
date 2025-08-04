package com.app.kotlinbasicslearn.workmanager.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kotlinbasicslearn.workmanager.api.models.StockNewsItem

@Dao
interface StockNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockNews(stockNews: List<StockNewsItem>)

    @Query("SELECT * FROM stock_news")
    suspend fun getStockNews(): List<StockNewsItem>
}