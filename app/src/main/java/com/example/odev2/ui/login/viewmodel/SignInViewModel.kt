package com.example.odev2.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.odev2.repository.Repository
import com.example.odev2.ui.login.state.SignInState
import com.example.odev2.util.validateEmailAndPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: Repository) : ViewModel() {
    private val _signInState = MutableStateFlow(SignInState())
    val signInState: StateFlow<SignInState> = _signInState.asStateFlow()

    fun setEmailAndPassword(email: String, password: String) {
        _signInState.value = _signInState.value.copy(email = email, password = password)
    }

    fun signIn() {
        val email = _signInState.value.email
        val password = _signInState.value.password

        if (validateEmailAndPassword(_signInState)) {
            viewModelScope.launch {
                val isUserValid = repository.getUserDao().isUserAndPasswordValid(email, password)
                if (isUserValid != null) {
                    _signInState.value = _signInState.value.copy(
                        isSignInSuccessful = true,
                        signInError = ""
                    )
                } else {
                    if (signInState.value.signInError.isEmpty()) {
                        _signInState.value = _signInState.value.copy(
                            isSignInSuccessful = false,
                            signInError = "Please, sign up first"
                        )
                    } else {
                        _signInState.value = _signInState.value.copy(
                            isSignInSuccessful = false,
                            //signInError = "Invalid email or password"
                        )
                    }

                }
            }
        }
    }
}

class SignInViewModelFactory (
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignInViewModel(repository) as T
    }
}