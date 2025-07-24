package com.app.kotlinbasicslearn.wallpaper.client

import android.service.wallpaper.WallpaperService
import com.app.kotlinbasicslearn.wallpaper.WallpaperItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {

    private const val BASE_URL = "https://picsum.photos/"

    val apiService: RetrofitApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitApiService::class.java)
    }


//    suspend fun getWallpapers(): List<WallpaperItem> {
//        return apiService.fetchWallpaper().map { it.toWallpaperItem() }
//    }

    private var currPage = 1
    private var endReached = false

    //lets use pagination
    suspend fun getWallpaper(): List<WallpaperItem> {

        if (endReached) return emptyList()

        val result = apiService.fetchWallpaper(currPage, 10)

        if (result.isEmpty()) {
            endReached = true
        } else {
            currPage++
        }

        return result.map { it.toWallpaperItem() }
    }


    //using singleton synchronized
//    object retrofitInstance {
//
//        @Volatile
//        private var instance: RetrofitApiService? = null
//
//        fun getInstance(): RetrofitApiService {
//            instance?.let {
//                return it
//            }
//            synchronized(this) {
//                val retrofit =
//                    Retrofit.Builder().baseUrl("https://picsum.photos/").addConverterFactory(
//                        GsonConverterFactory.create()
//                    ).build().create(RetrofitApiService::class.java)
//                instance = retrofit
//            }
//            return instance!!
//        }
//    }

}