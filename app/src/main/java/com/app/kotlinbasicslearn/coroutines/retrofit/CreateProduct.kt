package com.app.kotlinbasicslearn.coroutines.retrofit

data class CreateProduct(
    val title: String,
    val price: Float,
    val description: String,
    val categoryId: Int?,
    val images: List<String>
)
