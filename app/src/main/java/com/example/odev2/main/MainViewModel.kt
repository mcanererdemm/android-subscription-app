package com.example.odev2.ui.main.viewmodel

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.odev2.R
import com.example.odev2.data.database.entity.SubscriptionEntity
import com.example.odev2.data.model.Category
import com.example.odev2.data.model.Subscription
import com.example.odev2.data.model.SubscriptionCategory
import com.example.odev2.repository.RepositoryImp
import com.example.odev2.ui.main.home.HomeState
import com.example.odev2.util.CategoryTypeConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class MainViewModel(
    private val repository: RepositoryImp
) : ViewModel() {

    private val _subscriptionList = MutableStateFlow(HomeState())
    val subList = _subscriptionList.asStateFlow()

    private fun updateSubscriptionList(subscriptionList: List<Subscription>) {
        val sum = subscriptionList.sumOf { it.price.toInt() }
        _subscriptionList.value = HomeState(
            subscriptionList = subscriptionList, sumOfSubscription = sum
        )
    }

    fun addSubscription(subscription: Subscription) {
        viewModelScope.launch {
            try {
                val entity = subscription.toEntity()
                repository.getSubsDao().upsertSubscription(entity)
                getSubsListFromRepository()
            } catch (e: Exception) {
                Log.e("getSubsListFromRepository: ", "Failed to fetch subscriptions: ${e.message}")
            }
        }
    }

    fun getSubsListFromRepository() {
        viewModelScope.launch {
            try {
                val subscriptions = repository.getSubsDao().getSubscriptions()
                val mappedSubscriptions = subscriptions.mapNotNull { entity ->
                    try {
                        val category = CategoryTypeConverter.stringToCategory(entity.category)
                        Subscription(
                            id = entity.id,
                            name = entity.name,
                            price = entity.price,
                            category = category ?: throw Exception(
                                "getSubsListFromRepository -> Invalid category".repeat(
                                    10
                                )
                            ),
                            image = entity.image.toIntOrNull() ?: R.drawable.ic_launcher_background,
                            description = entity.description
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                updateSubscriptionList(mappedSubscriptions)
            } catch (e: Exception) {
                Log.e("getSubsListFromRepository: ", "Failed to fetch subscriptions: ${e.message}")
            }
        }
    }


    fun getSubscriptionsLimitedWithDate(selectedDate: Long) {
        viewModelScope.launch {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val allSubscriptions = repository.getSubsDao().getSubscriptions()

            val filteredSubscriptions = allSubscriptions.filter { entity ->
                // expireDate'i Long türüne dönüştürerek karşılaştırma yapıyoruz
                val expireDate = entity.expireDate.let { dateString ->
                    dateFormat.parse(dateString)?.time ?: Long.MAX_VALUE
                }
                expireDate >= selectedDate
            }.map { entity ->
                Subscription(
                    id = entity.id,
                    name = entity.name,
                    price = entity.price,
                    category = CategoryTypeConverter.stringToCategory(entity.category) ?: Category(
                        SubscriptionCategory.Other
                    ),
                    image = entity.image.toIntOrNull() ?: R.drawable.default_background,
                    description = entity.description,
                    registerDate = entity.registerDate,
                    expireDate = entity.expireDate
                )
            }

            updateSubscriptionList(filteredSubscriptions)
        }
    }

    fun deleteSubscription(subID: Int) {
        viewModelScope.launch {
            try {
                repository.getSubsDao().deleteSubscriptionWithId(subID)
                getSubsListFromRepository()
            } catch (e: Exception) {
                Log.e("deleteSubscription: ", "Failed to delete subscription: ${e.message}")
            }
        }
    }

    fun deleteAllSubs() {
        viewModelScope.launch {
            try {
                repository.getSubsDao().deleteAllSubscriptions()
                getSubsListFromRepository()
            } catch (e: Exception) {
                Log.e("deleteAllSubs: ", "Failed to delete subscriptions: ${e.message}")
            }
        }
    }

    private fun Subscription.toEntity(): SubscriptionEntity {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        // Get current time
        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentTime
        }

        // Format register date
        val registerDate = dateFormat.format(calendar.time)

        // Add one month and format expire date
        calendar.add(Calendar.MONTH, 1)
        val expireDate = dateFormat.format(calendar.time)

        return SubscriptionEntity(
            name = this.name,
            price = this.price,
            category = CategoryTypeConverter.categoryToString(
                Category(
                    name = this.category.name,
                    price = this.price,
                    limitPrice = ""
                )
            ),
            image = this.image.toString(),
            description = this.description,
            registerDate = registerDate,  // "dd-MM-yyyy" formatted string
            expireDate = expireDate      // "dd-MM-yyyy" formatted string
        )
    }
}

class MainViewModelFactory(private val repository: RepositoryImp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}