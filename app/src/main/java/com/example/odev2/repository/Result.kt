package com.example.odev2.repository

sealed class Result {
    class Success(val data: Any) : Result()
    class Error(val errorMessage: String) : Result()
}