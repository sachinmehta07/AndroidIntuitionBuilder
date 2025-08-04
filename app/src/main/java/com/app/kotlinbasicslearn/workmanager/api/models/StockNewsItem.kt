package com.app.kotlinbasicslearn.workmanager.api.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_news")
data class StockNewsItem(
    val imageUrl: String?, // <-- nullable now
    val pubDate: String?,
    val source: String,
    val summary: String,
    val title: String,
    val topics: List<String>,
    @PrimaryKey
    val url: String
)