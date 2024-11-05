package com.example.odev2.data.model

import java.io.Serializable

data class Category(
    val name: SubscriptionCategory = SubscriptionCategory.Other,
    val price: String = "",
    val limitPrice: String = "",
) : Serializable