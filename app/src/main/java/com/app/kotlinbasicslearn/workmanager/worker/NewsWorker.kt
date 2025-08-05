package com.app.kotlinbasicslearn.workmanager.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.kotlinbasicslearn.ProductApplication
import com.app.kotlinbasicslearn.workmanager.api.models.StockNewsItem
import com.app.kotlinbasicslearn.workmanager.utils.Resources

class NewsWorker(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {

        val newsRepository = (context.applicationContext as ProductApplication).NewsRepository

        return try {

            val result = newsRepository.getNewsBackground()
            if (result is Resources.Success && result.data.isNotEmpty()) {

                //as of now just printing data

                for (i in 0..<result.data.size) {
                    sendNewsNotification(result.data[i])
                }

                Result.success()

            } else {
                Result.failure()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }

    }

    private fun sendNewsNotification(newsItem: StockNewsItem) {
        // Normal Notification code here
        val TAG = "sendNewsNotification + NewsWorker WorkManger"
        Log.d(TAG, "sendNewsNotification: title " + newsItem.title)
        Log.d(TAG, "sendNewsNotification:  summary " + newsItem.summary)

    }
}