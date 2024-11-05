package com.example.odev2.util

import com.example.odev2.R
import com.example.odev2.data.model.Category
import com.example.odev2.data.model.Subscription
import com.example.odev2.data.model.SubscriptionCategory
import com.example.odev2.ui.login.state.SignInState
import com.example.odev2.ui.login.state.SignUpState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow

fun validateEmail(email: String, signInState: MutableStateFlow<SignInState>) {
    if (email.isEmpty()) {
        signInState.value = signInState.value.copy(
            emailError = "Email cannot be empty",
            isEmailValid = false,
            signInError = "Email cannot be empty"
        )
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        signInState.value = signInState.value.copy(
            emailError = "Email is not valid",
            isEmailValid = false,
            signInError = "Email is not valid"
        )
    } else {
        signInState.value = signInState.value.copy(
            emailError = null, isEmailValid = true
        )
    }
}

fun validatePassword(password: String, signInState: MutableStateFlow<SignInState>) {
    if (password.isEmpty()) {
        signInState.value = signInState.value.copy(
            passwordError = "Password cannot be empty",
            isPasswordValid = false,
            signInError = signInState.value.signInError + "\tPassword cannot be empty"
        )
    } else if (password.length < 6) {
        signInState.value = signInState.value.copy(
            passwordError = "Password must be at least 6 characters long",
            isPasswordValid = false,
            signInError = signInState.value.signInError + "\tPassword must be at least 6 characters long"
        )
    } else {
        signInState.value = signInState.value.copy(
            passwordError = null, isPasswordValid = true
        )
    }
}

fun validateEmailUp(email: String, signUpState: MutableStateFlow<SignUpState>) {
    signUpState.value = when {
        email.isEmpty() -> signUpState.value.copy(
            emailError = "Email cannot be empty",
            isEmailValid = false,
            signUpError = "Email cannot be empty"
        )

        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> signUpState.value.copy(
            emailError = "Email is not valid",
            isEmailValid = false,
            signUpError = "Email is not valid"
        )

        else -> signUpState.value.copy(emailError = null, isEmailValid = true)
    }
}

fun validatePasswordUp(password: String, signUpState: MutableStateFlow<SignUpState>) {
    signUpState.value = when {
        password.isEmpty() -> signUpState.value.copy(
            passwordError = "Password cannot be empty",
            isPasswordValid = false,
            signUpError = "Password cannot be empty"
        )

        password.length < 6 -> signUpState.value.copy(
            passwordError = "Password must be at least 6 characters long",
            isPasswordValid = false,
            signUpError = "Password must be at least 6 characters long"
        )

        else -> signUpState.value.copy(passwordError = null, isPasswordValid = true)
    }
}

fun validateRePassword(rePassword: String, signUpState: MutableStateFlow<SignUpState>) {
    signUpState.value = when {
        rePassword.isEmpty() -> signUpState.value.copy(
            rePasswordError = "Re-Password cannot be empty",
            isRePasswordValid = false,
            signUpError = "Re-Password cannot be empty"
        )

        rePassword != signUpState.value.password -> signUpState.value.copy(
            rePasswordError = "Passwords do not match",
            isRePasswordValid = false,
            signUpError = "Passwords do not match"
        )

        else -> signUpState.value.copy(rePasswordError = null, isRePasswordValid = true)
    }
}


fun validateEmailAndPassword(signInState: MutableStateFlow<SignInState>): Boolean {
    validateEmail(signInState.value.email, signInState)
    validatePassword(signInState.value.password, signInState)
    return (signInState.value.isEmailValid && signInState.value.isPasswordValid)
}

fun validateEmailAndPasswordAndRePassword(signUpState: MutableStateFlow<SignUpState>): Boolean {
    validateEmailUp(signUpState.value.email, signUpState)
    validatePasswordUp(signUpState.value.password, signUpState)
    validateRePassword(signUpState.value.rePassword, signUpState)
    return (signUpState.value.isEmailValid && signUpState.value.isPasswordValid && signUpState.value.isRePasswordValid)
}

object CategoryTypeConverter {
    private val gson = Gson()

    fun categoryToString(category: Category): String {
        return try {
            gson.toJson(category)
        } catch (e: Exception) {
            ""
        }
    }

    fun stringToCategory(categoryString: String): Category? {
        return try {
            gson.fromJson(categoryString, Category::class.java)
        } catch (e: Exception) {
            null
        }
    }
}

fun getSubscriptionList() = listOf(
    // adobe, airbnb, apple, applemusic, blutv, cambly, chycruchyroll, coursera, disneyplus, duolingo,
    // exxen, fitbit, github, hbo, netflix, notion, onedrive, privevideo, soundcloud, spotify, twitch,
    // uber, x, youtube, zoom

    Subscription(
        name = "Adobe",
        price = "20",
        image = R.drawable.adobe,
        category = Category(SubscriptionCategory.Productivity)
    ),
    Subscription(
        name = "Airbnb",
        price = "30",
        image = R.drawable.airbnb,
        category = Category(SubscriptionCategory.Accommodation)
    ),
    Subscription(
        name = "Apple",
        price = "15",
        image = R.drawable.apple,
        category = Category(SubscriptionCategory.Productivity)
    ),
    Subscription(
        name = "Apple Music",
        price = "10",
        image = R.drawable.applemusic,
        category = Category(SubscriptionCategory.Entertainment)
    ),
    Subscription(
        name = "BluTV",
        price = "40",
        image = R.drawable.blutv,
        category = Category(SubscriptionCategory.Entertainment)
    ),
    Subscription(
        name = "Cambly",
        price = "25",
        image = R.drawable.cambly,
        category = Category(SubscriptionCategory.Education)
    ),
    Subscription(
        name = "Crunchyroll",
        price = "10",
        image = R.drawable.chycrunchyroll,
        category = Category(SubscriptionCategory.Entertainment)
    ),
    Subscription(
        name = "Coursera",
        price = "50",
        image = R.drawable.coursera,
        category = Category(SubscriptionCategory.Education)
    ),
    Subscription(
        name = "Disney+",
        price = "15",
        image = R.drawable.disneyplus,
        category = Category(SubscriptionCategory.Entertainment)
    ),
    Subscription(
        name = "Duolingo",
        price = "12",
        image = R.drawable.duolingo,
        category = Category(SubscriptionCategory.Education)
    ),
    Subscription(
        name = "Exxen",
        price = "30",
        image = R.drawable.exxen,
        category = Category(SubscriptionCategory.Entertainment)
    ),
    Subscription(
        name = "Fitbit",
        price = "10",
        image = R.drawable.fitbit,
        category = Category(SubscriptionCategory.Health)
    ),
    Subscription(
        name = "GitHub",
        price = "8",
        image = R.drawable.github,
        category = Category(SubscriptionCategory.Productivity)
    ),
    Subscription(
        name = "HBO",
        price = "15",
        image = R.drawable.hbo,
        category = Category(SubscriptionCategory.Entertainment)
    ),
    Subscription(
        name = "Netflix",
        price = "45",
        image = R.drawable.netflix,
        category = Category(SubscriptionCategory.Entertainment)
    ),
    Subscription(
        name = "Notion",
        price = "8",
        image = R.drawable.notion,
        category = Category(SubscriptionCategory.Productivity)
    ),
    Subscription(
        name = "OneDrive",
        price = "7",
        image = R.drawable.onedrive,
        category = Category(SubscriptionCategory.Productivity)
    ),
    Subscription(
        name = "Prime Video",
        price = "15",
        image = R.drawable.primevideo,
        category = Category(SubscriptionCategory.Entertainment)
    ),
    Subscription(
        name = "SoundCloud",
        price = "10",
        image = R.drawable.soundcloud,
        category = Category(SubscriptionCategory.Entertainment)
    ),
    Subscription(
        name = "Spotify",
        price = "12",
        image = R.drawable.spotify_logo,
        category = Category(SubscriptionCategory.Entertainment)
    ),
    Subscription(
        name = "Twitch",
        price = "9",
        image = R.drawable.twitch,
        category = Category(SubscriptionCategory.Entertainment)
    ),
    Subscription(
        name = "Uber",
        price = "0",
        image = R.drawable.uber,
        category = Category(SubscriptionCategory.Transportation)
    ),
    Subscription(
        name = "X Premium",
        price = "8",
        image = R.drawable.x,
        category = Category(SubscriptionCategory.Social)
    ),
    Subscription(
        name = "YouTube",
        price = "13",
        image = R.drawable.youtube,
        category = Category(SubscriptionCategory.Entertainment)
    ),
    Subscription(
        name = "Zoom",
        price = "15",
        image = R.drawable.zoom,
        category = Category(SubscriptionCategory.Productivity)
    )
)