package com.app.kotlinbasicslearn.mvvm.sample.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuoteDao {

    @Insert
    suspend fun insertQuote(quote: Quote)

    @Query("SELECT * FROM quote")
    fun getQuotes(): LiveData<List<Quote>>

}