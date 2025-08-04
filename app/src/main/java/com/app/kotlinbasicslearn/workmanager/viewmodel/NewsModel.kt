package com.app.kotlinbasicslearn.workmanager.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.kotlinbasicslearn.workmanager.utils.NetworkUtils
import com.app.kotlinbasicslearn.workmanager.api.models.StockNewsItem
import com.app.kotlinbasicslearn.workmanager.repository.NewsRepository
import com.app.kotlinbasicslearn.workmanager.utils.ConnectionState
import com.app.kotlinbasicslearn.workmanager.utils.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//Use AndroidViewModel only when you truly need context. Otherwise, prefer plain ViewModel + utility functions.

class NewsModel(
    application: Application,
    private val repository: NewsRepository
) : AndroidViewModel(application) {
    val context: Context
        get() = getApplication<Application>().applicationContext
    private val _stockNews = MutableLiveData<Resources<List<StockNewsItem>>>()

    val stockNews: LiveData<Resources<List<StockNewsItem>>>
        get() = _stockNews

    private val _connectionState = MutableLiveData<ConnectionState>()
    val connectionState: LiveData<ConnectionState> = _connectionState

    private val allNews = mutableListOf<StockNewsItem>()

    init {
        fetchStockNews()
    }

    private fun fetchStockNews() {
        val hasNet = NetworkUtils.isInternetAvailable(context)

        _connectionState.postValue(if (hasNet) ConnectionState.Available else ConnectionState.Unavailable)

        if (hasNet) {
            getStockNews()
        } else {
            getStockNewsDb()
        }
    }

    private fun getStockNewsDb() {

        viewModelScope.launch(Dispatchers.IO) {
            Resources.Loading
            val result = repository.getNewsFromDb()
            if (result is Resources.Success) {
                allNews.addAll(result.data)
                _stockNews.postValue(Resources.Success(allNews.toList()))
            } else {
                Log.d("TAG", "getStockNewsDb: NOTHING")
            }

        }

    }

    private fun getStockNews() {

        viewModelScope.launch(Dispatchers.IO) {
            Resources.Loading
            val result = repository.getStockNews()
            if (result is Resources.Success) {
                allNews.addAll(result.data)
                _stockNews.postValue(Resources.Success(allNews.toList()))
            }
        }
    }


}