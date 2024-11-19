package com.example.mvvmtemplate.data.model

data class RefreshTokenRequest(
    val refreshToken:String,
    val expiresInMins : Int = 60
)
