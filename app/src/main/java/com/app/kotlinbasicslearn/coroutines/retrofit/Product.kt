package com.app.kotlinbasicslearn.coroutines.retrofit

data class Product(
    val id: Int?,
    val title: String,
    val price: Float,
    val description: String,
    val categoryId: Int?,
    val images: List<String>?,
    val category: Category?
)
