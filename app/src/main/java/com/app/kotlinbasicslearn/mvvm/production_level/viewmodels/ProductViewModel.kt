package com.app.kotlinbasicslearn.mvvm.production_level.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kotlinbasicslearn.coroutines.retrofit.Product
import com.app.kotlinbasicslearn.coroutines.retrofit.ProductUpdateRequest
import com.app.kotlinbasicslearn.mvvm.production_level.repository.ProductRepository
import kotlinx.coroutines.launch
import com.app.kotlinbasicslearn.coroutines.retrofit.CreateProduct
import com.app.kotlinbasicslearn.mvvm.production_level.utils.Resource
import kotlinx.coroutines.Dispatchers

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productList = MutableLiveData<Resource<List<Product>>>()
    val productList: LiveData<Resource<List<Product>>> = _productList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _createSuccess = MutableLiveData<Resource<Boolean>>()
    val createSuccess: LiveData<Resource<Boolean>> = _createSuccess

    private val _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess: LiveData<Boolean> = _updateSuccess

    private val _deleteSuccess = MutableLiveData<Boolean>()
    val deleteSuccess: LiveData<Boolean> = _deleteSuccess

    private val _latestCategoryId = MutableLiveData<Resource<Int?>>()
    val latestCategoryId: LiveData<Resource<Int?>> = _latestCategoryId

//    private val _categoryError = MutableLiveData<Boolean>()
//    val categoryError: LiveData<Boolean> = _categoryError


    init {
        fetchProducts()
    }

    //so here we used two approach for notifying activity about data change or API success

    //1st use of livedata with _isLoading which observe all the API hits and display progress as per isLoding true nd false set by api methods

    //2nd using sealed class where genric data class for all success nd failure data Display to activity

    private fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _productList.postValue(Resource.Loading)
            val result = repository.getProducts()
            _productList.postValue(result ?: Resource.Error("Empty List"))
        }
    }

    fun createProduct(product: CreateProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            _createSuccess.postValue(Resource.Loading)
            val success = repository.createProduct(product)
            _createSuccess.postValue(success)
            if (success is Resource.Success) fetchProducts()
        }
    }

    fun updateProduct(id: Int, product: ProductUpdateRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val success = repository.updateProduct(id, product)
            _updateSuccess.postValue(success)
            if (success) fetchProducts()
            _isLoading.postValue(false)
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val success = repository.deleteProduct(productId)
            _deleteSuccess.postValue(success)
            if (success) fetchProducts()
            _isLoading.postValue(false)
        }
    }

    fun fetchLatestCategoryId() {
        viewModelScope.launch(Dispatchers.IO) {
            _latestCategoryId.postValue(Resource.Loading)
            val id = repository.getLatestCategoryId()
            _latestCategoryId.postValue(id)
        }
    }

}