package com.app.kotlinbasicslearn.coroutines.retrofit

data class ProductUpdateRequest(
    val title: String? = null,
    val price: Float? = null,
    val description: String? = null,
    val images: List<String>? = null,
    val categoryId: Int? = null
)
