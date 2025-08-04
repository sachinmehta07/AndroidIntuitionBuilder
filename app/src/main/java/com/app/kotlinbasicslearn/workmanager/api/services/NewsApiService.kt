package com.app.kotlinbasicslearn.workmanager.api.services

import com.app.kotlinbasicslearn.workmanager.api.models.StockNews
import com.app.kotlinbasicslearn.workmanager.api.models.StockNewsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface NewsApiService {

//    val apiKey: String
//        get() = "sk-live-V0dV9JhHY6DIOvtVq96q2JwEzuQLk32VdvnXoRFK"

    @GET("news")
    suspend fun getStockNews(
        @Header("x-api-key") apiKey: String = "sk-live-V0dV9JhHY6DIOvtVq96q2JwEzuQLk32VdvnXoRFK"
    ): Response<List<StockNewsItem>>

}