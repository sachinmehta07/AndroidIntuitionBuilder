package com.app.kotlinbasicslearn.workmanager.utils

//why object in loading --> Use object when you only care that something is happening, not any data about it.

//why data class -->  You need to carry data

// Why data class?
//
//It holds data
//
//Auto-generates equals(), toString(), copy(), etc.
//
//Useful for comparing responses or modifying data immutably.

sealed class Resources<out T> {
    data object Loading : Resources<Nothing>()
    data class Success<out T>(val data: T) : Resources<T>()
    data class Error(val message: String) : Resources<Nothing>()

}

