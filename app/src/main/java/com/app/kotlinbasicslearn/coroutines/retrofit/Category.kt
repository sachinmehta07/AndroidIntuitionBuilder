package com.app.kotlinbasicslearn.coroutines.retrofit

import androidx.room.Entity


data class Category(
    val id: Int,
    val name: String,
    val slug: String,
    val image: String
)
