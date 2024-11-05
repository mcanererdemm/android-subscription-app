package com.example.odev2.ui.login.state

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val rePassword: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val rePasswordError: String? = null,
    val signUpError: String = "",
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isRePasswordValid: Boolean = false,
    val isSignUpSuccessful: Boolean = false
)