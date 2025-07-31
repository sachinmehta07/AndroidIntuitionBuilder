package com.app.kotlinbasicslearn.mvvm.production_level.utils

sealed class  Resource<out T> {

    object Loading : Resource<Nothing>() //NO data just indicating loading state
    data class Success<out T>(val data: T) : Resource<T>() // Has a real data
    data class Error(val message: String) : Resource<Nothing>() // Just error info

}