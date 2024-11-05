package com.example.odev2.repository

import com.example.odev2.data.database.dao.SubscriptionDao
import com.example.odev2.data.database.dao.UserDao
import com.example.odev2.data.database.db.SubscriptionDatabase
import com.example.odev2.data.database.db.UserDataBase

class RepositoryImp(
    private val subscriptionDatabase: SubscriptionDatabase,
    private val userDatabase: UserDataBase
) : Repository {

    override fun getSubsDao(): SubscriptionDao {
        return subscriptionDatabase.subscriptionDao()
    }

    override fun getUserDao(): UserDao {
        return userDatabase.userDao()
    }
}