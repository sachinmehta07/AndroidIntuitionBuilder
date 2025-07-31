package com.app.kotlinbasicslearn.mvvm.retrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kotlinbasicslearn.coroutines.retrofit.CreateProduct
import com.app.kotlinbasicslearn.coroutines.retrofit.Product
import com.app.kotlinbasicslearn.coroutines.retrofit.ProductUpdateRequest
import com.app.kotlinbasicslearn.mvvm.retrofit.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {


//    This is a standard design principle in clean architecture. You want data to flow one way:

    //User Action ➜ ViewModel ➜ MutableLiveData ➜ LiveData ➜ View observes

    val quotesList: LiveData<List<Product>>
        get() = repository.productsList


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProducts()
        }
    }

    //my mistakes while learning i thought it will return IMMEDIATELY  but it is not
//
//    fun createProduct(product: CreateProduct): Boolean {
//        var result = false
//        viewModelScope.launch(Dispatchers.IO) {
//            result = repository.createProduct(product)
//        }
//        return result /// 🚨 RETURNS IMMEDIATELY before coroutine completes
//    }

    //so for that we follow clean architecture pattern with production code level thinking

    private val _createProductResult = MutableLiveData<Boolean>()

    val createProductResult: LiveData<Boolean> = _createProductResult

    fun createProduct(product: CreateProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.createProduct(product)
            _createProductResult.postValue(result)
        }
    }

    private val _updateProductResult = MutableLiveData<Boolean>()

    val updateProductResult: LiveData<Boolean> = _updateProductResult

    fun updateProduct(id: Int, product: ProductUpdateRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateProduct(id, product)
            _updateProductResult.postValue(result)
        }

    }

    private var _deleteProductResult = MutableLiveData<Boolean>()
    val productDeleteResult: LiveData<Boolean> = _deleteProductResult

    fun deleteProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.deleteProduct(product)
            _deleteProductResult.postValue(result)
        }

    }

    private val _latestCategoryId = MutableLiveData<Int?>()
    val latestCategoryId: LiveData<Int?> = _latestCategoryId

    private val _categoryFetchError = MutableLiveData<Boolean>()
    val categoryFetchError: LiveData<Boolean> = _categoryFetchError

    fun fetchLatestCategoryId() {
        viewModelScope.launch {
            val latestId = repository.getLatestCategoryId()
            if (latestId != null) {
                _latestCategoryId.postValue(latestId)
                _categoryFetchError.postValue(false)
            } else {
                _categoryFetchError.postValue(true)
            }
        }
    }

}