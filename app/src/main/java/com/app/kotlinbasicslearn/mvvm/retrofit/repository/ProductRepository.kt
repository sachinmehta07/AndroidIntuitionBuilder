package com.app.kotlinbasicslearn.mvvm.retrofit.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kotlinbasicslearn.coroutines.retrofit.ApiService
import com.app.kotlinbasicslearn.coroutines.retrofit.CreateProduct
import com.app.kotlinbasicslearn.coroutines.retrofit.Product
import com.app.kotlinbasicslearn.coroutines.retrofit.ProductUpdateRequest
import com.app.kotlinbasicslearn.coroutines.retrofit.RetrofitInstance.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val productApiService: ApiService) {

    private val productListLiveData = MutableLiveData<List<Product>>()

    val productsList: LiveData<List<Product>>
        get() = productListLiveData

    //getting product list
    suspend fun getProducts() {
        val response = productApiService.getProducts()
        try {
            withContext(Dispatchers.Main) {
                run {
                    Log.d("RetrofitInstance", "fetchProducts: +  " + response.message())
                    Log.d("RetrofitInstance", "fetchProducts: +  " + response.isSuccessful)
                    Log.d("RetrofitInstance", "fetchProducts: +  " + response.body())
                    if (response.isSuccessful && response.body() != null) {
                        productListLiveData.postValue(response.body())
                    } else {
                        Log.d(
                            "RetrofitInstance",
                            "fetchProducts: FAIL+  " + response.errorBody().toString()
                        )
                        Log.d("RetrofitInstance", "fetchProducts:FAIL +  " + response.isSuccessful)
                        Log.d("RetrofitInstance", "fetchProducts: FAIL+  " + response.body())
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("TAG", "getProducts: " + e.message)
        }
    }

    suspend fun createProduct(product: CreateProduct): Boolean {
        val response = productApiService.createProduct(product)
        if (response.isSuccessful && response.body() != null) {
            Log.d("RetrofitInstance", "createProduct: +  " + response.message())
            Log.d("RetrofitInstance", "createProduct: +  " + response.isSuccessful)
            Log.d("RetrofitInstance", "createProduct: +  " + response.body())
            getProducts()
        } else {
            val errorString = response.errorBody()?.string()
            Log.e("CreateProduct", "Error Response: $errorString")
            Log.d("RetrofitInstance", "Error Body: " + response.errorBody()?.string())
            Log.d("RetrofitInstance", "createProduct: +  " + response.errorBody().toString())
            Log.d("RetrofitInstance", "createProduct: +  " + response.isSuccessful)
            Log.d("RetrofitInstance", "createProduct: +  " + response.code())
            Log.d("RetrofitInstance", "createProduct: +  " + response.headers())
            Log.d("RetrofitInstance", "createProduct: +  " + response.raw())
        }
        return response.isSuccessful
    }

    suspend fun updateProduct(id: Int, product: ProductUpdateRequest): Boolean {
        val response = productApiService.updateProduct(id, product)
        if (response.isSuccessful && response.body() != null) {
            getProducts()
            Log.d("RetrofitInstance", "updateProduct: +  " + response.isSuccessful)
            Log.d("RetrofitInstance", "updateProduct: +  " + response.body())
        } else {
            Log.d("RetrofitInstance", "updateProduct: +  " + response.errorBody().toString())
            Log.d("RetrofitInstance", "updateProduct: +  " + response.isSuccessful)
            Log.d("RetrofitInstance", "updateProduct: +  " + response.body())
        }
        return response.isSuccessful
    }

    suspend fun deleteProduct(product: Product): Boolean {
        val response = productApiService.deleteProduct(product.id!!)
        if (response.isSuccessful && response.body() != null) {
            getProducts()
            Log.d("RetrofitInstance", "deleteProduct: +  " + response.body())
            Log.d("RetrofitInstance", "updateProduct: +  " + response.isSuccessful)
        } else {
            Log.d("RetrofitInstance", "updateProduct: +  " + response.isSuccessful)
            Log.d("RetrofitInstance", "updateProduct: +  " + response.errorBody().toString())
            Log.d("RetrofitInstance", "updateProduct: +  " + response.body())
        }
        return response.isSuccessful
    }

    suspend fun getLatestCategoryId(): Int? {
        val response = productApiService.getCategories()
        return if (response.isSuccessful) {
            response.body()?.maxByOrNull { it.id }?.id
        } else {
            val errorString = response.errorBody()?.string()
            Log.e("CreateProduct", "Error Response: $errorString")
            Log.d("RetrofitInstance", "Error Body: " + response.errorBody()?.string())
            Log.d("RetrofitInstance", "getLatestCategoryId: +  " + response.errorBody().toString())
            Log.d("RetrofitInstance", "getLatestCategoryId: +  " + response.isSuccessful)
            Log.d("RetrofitInstance", "getLatestCategoryId: +  " + response.code())
            Log.d("RetrofitInstance", "getLatestCategoryId: +  " + response.headers())
            Log.d("RetrofitInstance", "getLatestCategoryId: +  " + response.raw())
            Log.e("Repo", "Error fetching categories: ${response.errorBody()?.string()}")
            null
        }
    }

}


