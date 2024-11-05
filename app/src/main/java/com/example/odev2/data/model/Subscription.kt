package com.example.odev2.data.model

import java.io.Serializable

data class Subscription(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val image: Int = 0,
    val category: Category,
    val description: String = "",
    val registerDate: String = "",
    val expireDate: String = ""
) : Serializable

enum class SubscriptionCategory {
    Transportation, Accommodation, Entertainment, Education, Health, Productivity, Social, Other
}