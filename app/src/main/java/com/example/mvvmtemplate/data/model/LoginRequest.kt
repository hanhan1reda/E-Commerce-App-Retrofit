package com.example.mvvmtemplate.data.model

data class LoginRequest(
    val username: String,
    val password: String,
    val expiresInMins: Int = 60
)
