package com.app.kotlinbasicslearn.roomdb.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonDao {

    //We used suspend DAO methods with lifecycleScope.launch {}
    // to keep DB operations off the main thread, preventing app freezes.

    //below method used to add the data base
    @Insert
    suspend fun insert(courseModel: Person)

    //update the db
    @Update
    suspend fun update(person: Person)

    @Delete
    suspend fun delete(person: Person)

    @Query("DELETE FROM Person")
    suspend fun deleteAll()

    @Query("SELECT * FROM Person ORDER BY id ASC")
    fun getAllCoursesAsc(): LiveData<List<Person>>

    @Query("SELECT * FROM Person ORDER BY id DESC")
    fun getAllCoursesDesc(): LiveData<List<Person>>

}