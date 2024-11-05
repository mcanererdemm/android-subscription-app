package com.example.odev2.data.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.odev2.data.database.dao.UserDao
import com.example.odev2.data.database.entity.User

@Database(entities = [User::class], version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "user_database1"

        @Volatile
        var INSTANCE: UserDataBase? = null
            private set

        fun getInstance(context: Context): UserDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}