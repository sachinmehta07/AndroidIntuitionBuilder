package com.app.kotlinbasicslearn.mvvm.quote.modelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.kotlinbasicslearn.mvvm.quote.viewmodel.QuoteViewModel

class QuoteViewModelFactory(val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuoteViewModel::class.java)) {
            return QuoteViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}  