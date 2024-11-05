package com.example.odev2.ui.login.state

data class SignInState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = "",
    val passwordError: String? = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isSignInSuccessful: Boolean = false,
    var signInError: String = ""
)
