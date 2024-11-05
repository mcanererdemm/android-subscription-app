package com.example.odev2.repository

import com.example.odev2.data.database.dao.SubscriptionDao
import com.example.odev2.data.database.dao.UserDao

interface Repository {
    fun getSubsDao(): SubscriptionDao
    fun getUserDao(): UserDao
}