package com.app.kotlinbasicslearn.mvvm.sample.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Quote::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getDb(context: Context): AppDatabase {

            INSTANCE?.let { return it }

            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "quotes_database"
                    ).createFromAsset("quotes.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}