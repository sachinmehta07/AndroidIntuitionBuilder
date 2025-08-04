package com.app.kotlinbasicslearn.mvvm.production_level.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kotlinbasicslearn.coroutines.retrofit.CreateProduct
import com.app.kotlinbasicslearn.coroutines.retrofit.Product
import com.app.kotlinbasicslearn.coroutines.retrofit.ProductUpdateRequest
import com.app.kotlinbasicslearn.mvvm.production_level.repository.ProductRepository
import com.app.kotlinbasicslearn.mvvm.production_level.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductViewModel(
    private val context: Context,
    private val repository: ProductRepository
) : ViewModel() {

    private val _productList = MutableLiveData<Resource<List<Product>?>>()
    val productList: LiveData<Resource<List<Product>?>> = _productList

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

    private val allProducts = mutableListOf<Product>()
    private var isCurrentlyLoading = false
    private var currentOffset = 0
    private var endReached = false
    private val pageSize = 20

//    private val _categoryError = MutableLiveData<Boolean>()
//    val categoryError: LiveData<Boolean> = _categoryError


    init {

        Log.d("isInternetAvailable", "Checking: +  " + isInternetAvailable(context))
        if (isInternetAvailable(context)) {
            Log.d("isInternetAvailable", "isInternetAvailable: +  ")
            fetchProducts()
        } else {
            Log.d("isInternetAvailable", "getProductsDb: +  ")
            getProductsDb()
        }
    }

    //so here we used two approach for notifying activity about data change or API success

    //1st use of livedata with _isLoading which observe all the API hits and display progress as per isLoding true nd false set by api methods

    //2nd using sealed class where genric data class for all success nd failure data Display to activity

//    private fun fetchProducts(
//        limit: Int = 20,
//        offset: Int = 0
//    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _productList.postValue(Resource.Loading)
//            val result = repository.getProducts(limit, offset)
//            _productList.postValue(result ?: Resource.Error("Empty List"))
//        }
//    }

    private fun getProductsDb() {
        Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _productList.postValue(Resource.Loading)
            val result = repository.getProductsDb()
            Log.d("getProductsDb", "Offline data size: ${(result as? Resource.Success)?.data?.size}")
            _productList.postValue(result)
        }
    }

    private fun fetchProducts(limit: Int = pageSize, offset: Int = 0) {
        Log.d("TAG", "fetchProducts: $isCurrentlyLoading")
        Log.d("TAG", "fetchProducts: $endReached")
        if (isCurrentlyLoading || endReached) return

        isCurrentlyLoading = true

        _productList.postValue(Resource.Loading)
        Log.d("TAG", "fetchProducts: ")
        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.getProducts(limit, offset)

            if (result is Resource.Success) {

                if (offset == 0) allProducts.clear() // Refresh case

                allProducts.addAll(result.data)
                Log.d("TAG", "fetchProducts: " + allProducts.size)
                // If fewer than expected, assume end reached
                if (result.data.size < limit) {
                    endReached = true
                }

                currentOffset = allProducts.size
                _productList.postValue(Resource.Success(allProducts.toList()))

            } else {
                _productList.postValue(result ?: Resource.Error("Unknown Error"))
            }

            isCurrentlyLoading = false

        }
    }

    fun createProduct(product: CreateProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            _createSuccess.postValue(Resource.Loading)
            val success = repository.createProduct(product)
            _createSuccess.postValue(success)
            if (success is Resource.Success) refreshProducts()
        }
    }

    fun updateProduct(id: Int, product: ProductUpdateRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val success = repository.updateProduct(id, product)
            _updateSuccess.postValue(success)
            if (success) refreshProducts()
            _isLoading.postValue(false)
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val success = repository.deleteProduct(productId)
            _deleteSuccess.postValue(success)
            if (success) refreshProducts()
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

    fun loadMoreProducts(
        isUIBusy: Boolean,
        lastVisibleItemPosition: Int,
        totalItemCount: Int
    ) {
        if (!isUIBusy && !isCurrentlyLoading && !endReached && lastVisibleItemPosition >= totalItemCount - 5) {
            fetchProducts(offset = currentOffset)
        }
    }

    private fun refreshProducts() {
        // Reset pagination state and fetch first page again
        currentOffset = 0
        endReached = false
        fetchProducts(offset = 0)
    }

    fun isInternetAvailable(context: Context = this.context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}