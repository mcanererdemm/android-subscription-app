package com.example.odev2.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.odev2.data.database.entity.SubscriptionEntity

@Dao
interface SubscriptionDao {
    @Upsert
    suspend fun upsertSubscription(subscription: SubscriptionEntity)

    @Query("SELECT * FROM subscriptions1")
    suspend fun getSubscriptions(): List<SubscriptionEntity>

    @Query("DELETE FROM subscriptions1 WHERE id = :id")
    suspend fun deleteSubscriptionWithId(id: Int)

    @Query("DELETE FROM subscriptions1")
    suspend fun deleteAllSubscriptions()

    @Query("SELECT * FROM subscriptions1 WHERE category = :category")
    suspend fun getSubscriptionsByCategory(category: String): List<SubscriptionEntity>

    @Query("SELECT * FROM subscriptions1 WHERE expireDate < :currentDate ORDER BY expireDate DESC")
    suspend fun getNotExpiredSubscriptions(currentDate: String): List<SubscriptionEntity>

    @Query("SELECT * FROM subscriptions1 WHERE expireDate >= :currentDate ORDER BY expireDate DESC")
    suspend fun getExpiredSubscriptions(currentDate: String): List<SubscriptionEntity>


}