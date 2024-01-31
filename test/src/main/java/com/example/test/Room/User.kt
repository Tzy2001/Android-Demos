package com.example.test.Room

import androidx.room.Entity
import androidx.room.PrimaryKey


//用戶類
@Entity
data class User(var firstName: String, var lastName: String, var age: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}