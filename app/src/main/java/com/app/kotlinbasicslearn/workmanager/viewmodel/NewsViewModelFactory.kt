package com.app.kotlinbasicslearn.workmanager.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.kotlinbasicslearn.workmanager.repository.NewsRepository

class NewsViewModelFactory(
    private val context: Context,
    private val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsModel(context, newsRepository) as T
    }

}