package com.app.kotlinbasicslearn.roomdb.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DemoDao {
    @Insert
    //we use suspend fun to keep db operation off from main thread
    suspend fun insert(demo: Demo)

    @Update
    suspend fun update(demo: Demo)

    @Query("SELECT * FROM DEMO ORDER by id ASC")
    fun getAllDemo(): LiveData<List<Demo>>

// LiveData : Room automatically watches the table

}