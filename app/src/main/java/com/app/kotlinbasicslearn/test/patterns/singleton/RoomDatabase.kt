package com.app.kotlinbasicslearn.test.patterns.singleton

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.kotlinbasicslearn.roomdb.model.Person
import com.app.kotlinbasicslearn.roomdb.model.PersonDao

@Database(entities = [Person::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabase? = null

        fun getDb(context: Context): RoomDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            RoomDatabase::class.java,
                            "person_db"
                        ).build()
                }
            }
            return INSTANCE!!
        }

    }

}