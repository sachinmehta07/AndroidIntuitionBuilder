package com.app.kotlinbasicslearn.mvvm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.kotlinbasicslearn.mvvm.viewmodel.MainViewModel

class MainViewModelFactory(val count: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(count) as T
    }

}