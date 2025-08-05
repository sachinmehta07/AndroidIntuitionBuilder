package com.app.kotlinbasicslearn.workmanager.repository

import android.util.Log
import com.app.kotlinbasicslearn.mvvm.production_level.utils.Resource
import com.app.kotlinbasicslearn.workmanager.api.models.StockNewsItem
import com.app.kotlinbasicslearn.workmanager.api.services.NewsApiService
import com.app.kotlinbasicslearn.workmanager.db.dao.StockNewsDao
import com.app.kotlinbasicslearn.workmanager.utils.Resources

class NewsRepository(
    private val newsApiService: NewsApiService,
    private val stockNewsDao: StockNewsDao
) {

    suspend fun getStockNews(): Resources<List<StockNewsItem>> {
        val response = newsApiService.getStockNews()
        Log.d("RetrofitInstance", "getStockNews: +  " + response.message())
        Log.d("RetrofitInstance", "getStockNews: +  " + response.errorBody().toString())
        Log.d("RetrofitInstance", "getStockNews: +  " + response.isSuccessful)
        Log.d("RetrofitInstance", "getStockNews: +  " + response.body())
        return if (response.isSuccessful) {
            stockNewsDao.insertStockNews(response.body() ?: emptyList())
            Resources.Success(response.body() ?: emptyList())
        } else {
            Resources.Error(response.message())
        }
    }

    suspend fun getNewsFromDb(): Resources<List<StockNewsItem>> {
        val response = stockNewsDao.getStockNews()
        Log.d("DB", "Fetched from DB: ${response.size}")
        if (response.isNotEmpty()) {
            return Resources.Success(response)
        }
        return Resources.Error("No data found")

    }

    suspend fun getNewsBackground(): Resources<List<StockNewsItem>> {

        val response = newsApiService.getStockNews()
        Log.d("getNewsBackground", "getStockNews: +  " + response.message())
        Log.d("getNewsBackground", "getStockNews: +  " + response.errorBody().toString())
        Log.d("getNewsBackground", "getStockNews: +  " + response.isSuccessful)
        Log.d("getNewsBackground", "getStockNews: +  " + response.body())
        return if (response.isSuccessful) {
            stockNewsDao.insertStockNews(response.body() ?: emptyList())
            Resources.Success(response.body() ?: emptyList())
        } else {
            Resources.Error(response.message())
        }

    }

}