package com.app.kotlinbasicslearn.wallpaper.client

import com.app.kotlinbasicslearn.wallpaper.model.WallPaperResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {

    //manual way of handling page nd limit
//    @GET("v2/list?page=1&limit=100")
//    suspend fun fetchWallpaper() : List<WallPaperResponse>

    //Dynamic way
    @GET("v2/list")
    suspend fun fetchWallpaper(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): List<WallPaperResponse>

}