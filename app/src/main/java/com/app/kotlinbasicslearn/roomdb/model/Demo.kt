package com.app.kotlinbasicslearn.roomdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Demo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val personName: String,
    val personAge: Int,
)

