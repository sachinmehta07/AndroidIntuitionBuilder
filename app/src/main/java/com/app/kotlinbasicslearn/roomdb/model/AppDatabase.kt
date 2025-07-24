package com.app.kotlinbasicslearn.roomdb.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

@Database(entities = [Person::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao

    //    Define static-like methods & vars
    companion object {


        //When migration of new table content in exiting db or table is required
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(connection: SQLiteConnection) {
                connection.execSQL("ALTER TABLE Person ADD COLUMN personGender TEXT NOT NULL DEFAULT 'NOT MENTIONED' ")
            }
        }


        //Volatile Ensure thread-safe visibility of a variable
        @Volatile
        //	Singleton DB instance
        private var INSTANCE: AppDatabase? = null

        //Advance Syntax

//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "person_db"
//                ).build()
//
//                INSTANCE = instance
//                instance
//            }
//        }

        //Easy Syntax

        fun getDatabase(context: Context): AppDatabase {

            // Step 1: If INSTANCE already exists, return it immediately
//        if (INSTANCE != null) {
//            return INSTANCE!!
//        }

            INSTANCE?.let { return it }

            // Step 2: Else, enter synchronized block to create instance safely
            synchronized(this) {
                // Double-check again inside the lock
                if (INSTANCE == null) {
                    val createdInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "person_db"
                    ).addMigrations(MIGRATION_1_2).build()

                    INSTANCE = createdInstance
                }

                // Return the instance (whether newly created or already there)
                return INSTANCE!!
            }
        }

    }
}