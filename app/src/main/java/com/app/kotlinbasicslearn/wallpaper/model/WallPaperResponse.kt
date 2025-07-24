package com.app.kotlinbasicslearn.wallpaper.model

import com.app.kotlinbasicslearn.wallpaper.WallpaperItem

data class WallPaperResponse(
    val download_url: String,
    val id: String
) {
    fun toWallpaperItem() = WallpaperItem(id = id, imageUrl = download_url)
}
