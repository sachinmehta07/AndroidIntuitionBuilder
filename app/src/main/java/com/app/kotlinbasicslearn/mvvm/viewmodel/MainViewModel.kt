package com.app.kotlinbasicslearn.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(initialCount: Int) : ViewModel() {

    private val countLiveDataObject = MutableLiveData<Int>(initialCount)

    val url = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_960_720.jpg"

    //Imputable LiveData
    val countLiveData: LiveData<Int> = countLiveDataObject

    var count: Int = countLiveDataObject.value ?: 0

    fun updateCountLiveData(newCount: Int) {
        countLiveDataObject.value = newCount
        count = newCount
    }

    //for data binding
    fun increaseCount() {
        count++
        countLiveDataObject.value = count
    }

    fun decreaseCount() {
        if (count > 0) {
            count--
            countLiveDataObject.value = count
        }
    }

//    fun increaseCount() {
//        count++
//    }
//
//    fun decreaseCount() {
//        if (count >= 0) {
//            count--
//        }
//    }


}