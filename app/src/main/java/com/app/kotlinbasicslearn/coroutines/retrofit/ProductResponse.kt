package com.app.kotlinbasicslearn.coroutines.retrofit

data class ProductResponse(
    val id: Int,
    val title: String,
    val slug: String,
    val price: Float,
    val description: String,
    val images: List<String>,
    val category: Category
)

