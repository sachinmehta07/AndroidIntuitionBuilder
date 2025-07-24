package com.app.kotlinbasicslearn.mvvm.sample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kotlinbasicslearn.mvvm.sample.repository.QuoteRepository
import com.app.kotlinbasicslearn.mvvm.sample.roomDB.Quote
import kotlinx.coroutines.launch

class QuoteViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    fun getQuotes() = quoteRepository.getQuotes()

    fun insertQuote(quote: Quote) {
        viewModelScope.launch {
            quoteRepository.insertQuotes(quote)
        }
    }

}