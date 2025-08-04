package com.app.kotlinbasicslearn.mvvm.production_level.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kotlinbasicslearn.coroutines.retrofit.Product


@Dao
interface ProductDao {
//“If the primary key already exists, replace it with the new data — no crash, no duplicates.”
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Query("SELECT * FROM products")
    fun getProducts(): List<Product>

}