package com.app.kotlinbasicslearn.mvvm.sample.repository

import com.app.kotlinbasicslearn.mvvm.sample.roomDB.QuoteDao
import com.app.kotlinbasicslearn.mvvm.sample.roomDB.Quote

class QuoteRepository(private val quoteDao: QuoteDao) {

    //get the data
    fun getQuotes() = quoteDao.getQuotes()

    //insert the data
    suspend fun insertQuotes(quote: Quote) = quoteDao.insertQuote(quote)

}