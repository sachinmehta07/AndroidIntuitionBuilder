package com.app.kotlinbasicslearn.mvvm.production_level.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters
import com.app.kotlinbasicslearn.coroutines.retrofit.Product
import com.app.kotlinbasicslearn.mvvm.production_level.db.dao.ProductDao
import com.app.kotlinbasicslearn.mvvm.production_level.db.entity.Converters
import com.app.kotlinbasicslearn.roomdb.model.Person
import com.app.kotlinbasicslearn.roomdb.model.PersonDao
import com.app.kotlinbasicslearn.workmanager.api.models.StockNewsItem
import com.app.kotlinbasicslearn.workmanager.db.dao.StockNewsDao

@Database(entities = [Product::class, Person::class , StockNewsItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class RoomDb : androidx.room.RoomDatabase() {

    abstract fun StockNewsDao(): StockNewsDao

    abstract fun productDao(): ProductDao

    abstract  fun personDao(): PersonDao

    companion object {

        private var INSTANCE: RoomDb? = null

        fun getDb(context: Context): RoomDb {

            INSTANCE?.let { return it }

            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDb::class.java,
                        "demo_db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}