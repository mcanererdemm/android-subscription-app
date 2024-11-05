package com.example.odev2.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions1")
data class SubscriptionEntity(
    val name: String = "",
    val price: String = "",
    val image: String = "",
    val category: String = "",
    val description: String = "",
    val registerDate: String = "",
    val expireDate: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
