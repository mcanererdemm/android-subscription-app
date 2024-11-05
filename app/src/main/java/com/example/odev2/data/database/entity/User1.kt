package com.example.odev2.data.database.entity

import androidx.room.Entity

@Entity(tableName = "users1", primaryKeys = ["email"])
data class User(
    val email: String = "",
    val password: String = "",
    val lastLoginDate: String = "",
)