package com.app.kotlinbasicslearn.coroutines.retrofit

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)val id: Int?,
    val title: String,
    val price: Float,
    val description: String,
    val categoryId: Int?,
    val images: List<String>?,
    var category: Category? = null  // this uses @TypeConverter
)
