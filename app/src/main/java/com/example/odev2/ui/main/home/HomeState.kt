package com.example.odev2.ui.main.home

import com.example.odev2.data.model.Subscription

data class HomeState(
    val subscriptionList: List<Subscription> = emptyList(),
    val sumOfSubscription: Int = 0
)