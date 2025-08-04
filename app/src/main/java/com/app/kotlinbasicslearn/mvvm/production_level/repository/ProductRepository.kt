package com.app.kotlinbasicslearn.mvvm.production_level.repository


import android.util.Log
import androidx.lifecycle.LiveData
import com.app.kotlinbasicslearn.coroutines.retrofit.ApiService
import com.app.kotlinbasicslearn.coroutines.retrofit.CreateProduct
import com.app.kotlinbasicslearn.coroutines.retrofit.Product
import com.app.kotlinbasicslearn.coroutines.retrofit.ProductUpdateRequest
import com.app.kotlinbasicslearn.mvvm.production_level.db.dao.ProductDao
import com.app.kotlinbasicslearn.mvvm.production_level.utils.Resource

class ProductRepository(
    private val productApiService: ApiService, private val productDao: ProductDao
) {

    suspend fun getProducts(
        limit: Int = 20, offset: Int = 0
    ): Resource<List<Product>>? {

        return try {

            val response = productApiService.getProducts(limit, offset)

            Log.d("RetrofitInstance", "fetchProducts: +  " + response.message())
            Log.d("RetrofitInstance", "fetchProducts: +  " + response.errorBody().toString())
            Log.d("RetrofitInstance", "fetchProducts: +  " + response.isSuccessful)
            Log.d("RetrofitInstance", "fetchProducts: +  " + response.body())

            if (response.isSuccessful) {

                productDao.insertProducts(response.body() ?: emptyList())

                Resource.Success(response.body() ?: emptyList())
            } else {
                Resource.Error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            Log.d("TAG", "getProducts: $e")
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun createProduct(product: CreateProduct): Resource<Boolean> {
        return try {
            val response = productApiService.createProduct(product)

            Log.d("RetrofitInstance", "createProduct: +  " + response.message())
            Log.d("RetrofitInstance", "createProduct: +  " + response.errorBody().toString())
            Log.d("RetrofitInstance", "createProduct: +  " + response.isSuccessful)
            Log.d("RetrofitInstance", "createProduct: +  " + response.body())

            if (response.isSuccessful) {
                Resource.Success(response.isSuccessful)
            } else {
                Resource.Error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun updateProduct(id: Int, product: ProductUpdateRequest): Boolean {
        return try {
            val response = productApiService.updateProduct(id, product)

            Log.d("RetrofitInstance", "updateProduct: +  " + response.message())
            Log.d("RetrofitInstance", "updateProduct: +  " + response.errorBody().toString())
            Log.d("RetrofitInstance", "updateProduct: +  " + response.isSuccessful)
            Log.d("RetrofitInstance", "updateProduct: +  " + response.body())

            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteProduct(productId: Int): Boolean {
        return try {
            val response = productApiService.deleteProduct(productId)

            Log.d("RetrofitInstance", "deleteProduct: +  " + response.message())
            Log.d("RetrofitInstance", "deleteProduct: +  " + response.errorBody().toString())
            Log.d("RetrofitInstance", "deleteProduct: +  " + response.isSuccessful)
            Log.d("RetrofitInstance", "deleteProduct: +  " + response.body())

            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getLatestCategoryId(): Resource<Int?> {
        return try {
            val response = productApiService.getCategories()

            Log.d("RetrofitInstance", "getLatestCategoryId: +  " + response.message())
            Log.d("RetrofitInstance", "getLatestCategoryId: +  " + response.errorBody().toString())
            Log.d("RetrofitInstance", "getLatestCategoryId: +  " + response.isSuccessful)
            Log.d("RetrofitInstance", "getLatestCategoryId: +  " + response.body())

            if (response.isSuccessful) {
                Resource.Success(response.body()?.maxByOrNull { it.id }?.id)
            } else {
                Resource.Error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.Error(e.toString())
        }
    }

    fun getProductsDb(): Resource<List<Product>?> {
        val localData = productDao.getProducts()
        Log.d("DB", "Fetched from DB: ${localData.size}")
        return if (localData.isNotEmpty()) {
            return Resource.Success(localData)
        } else {
            Resource.Error("No local data found")
        }
    }


}