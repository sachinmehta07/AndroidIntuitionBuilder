package com.app.kotlinbasicslearn

import android.app.Application
import com.app.kotlinbasicslearn.coroutines.retrofit.ApiService
import com.app.kotlinbasicslearn.coroutines.retrofit.RetrofitInstance
import com.app.kotlinbasicslearn.mvvm.production_level.db.RoomDb
import com.app.kotlinbasicslearn.mvvm.production_level.repository.ProductRepository
import com.app.kotlinbasicslearn.workmanager.api.client.RetrofitClient
import com.app.kotlinbasicslearn.workmanager.repository.NewsRepository

class ProductApplication : Application() {

    lateinit var productRepository: ProductRepository

    lateinit var NewsRepository: NewsRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {

//        val productService = RetrofitInstance.apiService
//        val database = RoomDb.getDb(applicationContext)
//        productRepository = ProductRepository(productService, database.productDao())

        val newsApiService = RetrofitClient.newsApiService
        val databaseNews = RoomDb.getDb(applicationContext)
        NewsRepository = NewsRepository(newsApiService, databaseNews.StockNewsDao())


    }

}