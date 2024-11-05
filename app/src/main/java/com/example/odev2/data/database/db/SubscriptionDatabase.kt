package com.example.odev2.data.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.odev2.data.database.dao.SubscriptionDao
import com.example.odev2.data.database.entity.SubscriptionEntity

@Database(entities = [SubscriptionEntity::class], version = 1)
abstract class SubscriptionDatabase : RoomDatabase() {
    abstract fun subscriptionDao(): SubscriptionDao

    companion object {
        const val DATABASE_NAME = "subscription_database1"

        @Volatile
        var INSTANCE: SubscriptionDatabase? = null
            private set

        fun getInstance(context: Context): SubscriptionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SubscriptionDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}