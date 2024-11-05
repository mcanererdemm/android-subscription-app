package com.example.odev2.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.odev2.data.database.entity.User
import com.example.odev2.repository.Repository
import com.example.odev2.ui.login.state.SignUpState
import com.example.odev2.util.validateEmailUp
import com.example.odev2.util.validatePasswordUp
import com.example.odev2.util.validateRePassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar


class SignUpViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    fun setEmailAndPasswordAndRePassword(email: String, password: String, rePassword: String) {
        _signUpState.value = _signUpState.value.copy(email = email, password = password, rePassword = rePassword)
        validateInput()
    }

    private fun validateInput() {
        validateEmailUp(_signUpState.value.email, _signUpState)
        validatePasswordUp(_signUpState.value.password, _signUpState)
        validateRePassword(_signUpState.value.rePassword, _signUpState)
    }

    fun signUp() {
        if (_signUpState.value.isEmailValid && _signUpState.value.isPasswordValid && _signUpState.value.isRePasswordValid) {
            viewModelScope.launch {
                repository.getUserDao().upsertUser(
                    User(
                        email = _signUpState.value.email,
                        password = _signUpState.value.password,
                        lastLoginDate = Calendar.getInstance().timeInMillis.toString()
                    )
                )
                _signUpState.value = _signUpState.value.copy(isSignUpSuccessful = true, signUpError = "")
            }
        }
    }
}


class SignUpViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel(repository) as T
    }
}