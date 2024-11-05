package com.example.odev2.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.odev2.data.database.entity.User

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(user: User)

    @Query("SELECT * FROM users1 WHERE email = :email")
    suspend fun getUser(email: String): User?

    @Query("SELECT * FROM users1")
    suspend fun getAllUsers(): List<User>

    @Query("DELETE FROM users1")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM users1 ORDER BY lastLoginDate DESC")
    suspend fun getUsersSortedByLastLoginDate(): List<User>

    @Query("SELECT * FROM users1 WHERE email = :email AND password = :password")
    suspend fun isUserAndPasswordValid(email: String, password: String): User?
}