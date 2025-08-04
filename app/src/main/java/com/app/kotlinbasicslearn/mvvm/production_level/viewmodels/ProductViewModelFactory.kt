package com.app.kotlinbasicslearn.mvvm.production_level.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.kotlinbasicslearn.mvvm.production_level.repository.ProductRepository

class ProductViewModelFactory(
    private val context: Context,
    private val repository: ProductRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(context, repository) as T
    }
}