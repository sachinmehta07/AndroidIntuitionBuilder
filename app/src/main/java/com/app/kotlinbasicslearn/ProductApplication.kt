package com.app.kotlinbasicslearn

import android.app.Application
import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.app.kotlinbasicslearn.coroutines.retrofit.ApiService
import com.app.kotlinbasicslearn.coroutines.retrofit.RetrofitInstance
import com.app.kotlinbasicslearn.mvvm.production_level.db.RoomDb
import com.app.kotlinbasicslearn.mvvm.production_level.repository.ProductRepository
import com.app.kotlinbasicslearn.workmanager.api.client.RetrofitClient
import com.app.kotlinbasicslearn.workmanager.repository.NewsRepository
import com.app.kotlinbasicslearn.workmanager.worker.NewsWorker
import java.util.concurrent.TimeUnit

class ProductApplication : Application() {

    lateinit var productRepository: ProductRepository

    lateinit var NewsRepository: NewsRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
        enqueueNewsNotification(this) // schedules at launch
    }

    private fun initialize() {

//        val productService = RetrofitInstance.apiService
//        val database = RoomDb.getDb(applicationContext)
//        productRepository = ProductRepository(productService, database.productDao())

        val newsApiService = RetrofitClient.newsApiService
        val databaseNews = RoomDb.getDb(applicationContext)
        NewsRepository = NewsRepository(newsApiService, databaseNews.StockNewsDao())


    }

    private fun enqueueNewsNotification(context: Context) {

        //For only first time trigger worker

//        val request = OneTimeWorkRequestBuilder<NewsWorker>()
//            .setBackoffCriteria(
//                BackoffPolicy.EXPONENTIAL,
//                1, TimeUnit.MINUTES
//            ).build()
//
//        WorkManager.getInstance(context).enqueue(request)

        //any constraints

        val constraints = androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        //for repeat interval


        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<NewsWorker>(15, TimeUnit.MINUTES) //  Main repeat interval
                //below code saying Delay before retrying a failed job >> Only if Result.retry()
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 10, TimeUnit.SECONDS).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "news_notification",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )


    }

}