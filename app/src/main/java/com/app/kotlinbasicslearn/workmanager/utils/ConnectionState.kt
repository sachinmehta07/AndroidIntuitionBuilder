package com.app.kotlinbasicslearn.workmanager.utils

sealed class ConnectionState {
    data object Available : ConnectionState()
    data object Unavailable : ConnectionState()
}