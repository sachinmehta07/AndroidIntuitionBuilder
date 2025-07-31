package com.app.kotlinbasicslearn.mvvm.retrofit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.kotlinbasicslearn.mvvm.retrofit.repository.ProductRepository

class ProductViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(repository) as T
    }
}