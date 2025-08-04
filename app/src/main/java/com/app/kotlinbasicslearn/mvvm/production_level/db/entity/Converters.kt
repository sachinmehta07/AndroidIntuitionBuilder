package com.app.kotlinbasicslearn.mvvm.production_level.db.entity

import androidx.room.TypeConverter
import com.app.kotlinbasicslearn.coroutines.retrofit.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
//    @TypeConverter
//    fun fromStringList(value: List<String>?): String {
//        return Gson().toJson(value)
//    }

    @TypeConverter
    fun toStringList(value: String): List<String>? {
        return Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromCategory(category: Category?): String {
        return Gson().toJson(category)
    }

    @TypeConverter
    fun toCategory(value: String): Category? {
        return Gson().fromJson(value, Category::class.java)
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return Gson().toJson(value)
    }

    
}