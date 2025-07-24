package com.app.kotlinbasicslearn.mvvm.sample.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.kotlinbasicslearn.mvvm.sample.repository.QuoteRepository
import com.app.kotlinbasicslearn.mvvm.sample.viewmodel.QuoteViewModel

class QuoteViewModelFactory(private val quoteRepository: QuoteRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuoteViewModel(quoteRepository) as T
    }

}