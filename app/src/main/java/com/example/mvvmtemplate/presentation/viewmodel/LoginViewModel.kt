package com.example.mvvmtemplate.presentation.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtemplate.data.model.LoginRequest
import com.example.mvvmtemplate.data.model.LoginResponse
import com.example.mvvmtemplate.data.model.RefreshTokenRequest
import com.example.mvvmtemplate.data.model.RefreshTokenResponse
import com.example.mvvmtemplate.data.model.User
import com.example.mvvmtemplate.data.network.ApiService
import com.example.mvvmtemplate.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val apiService: ApiService,private val authInterceptor: AuthInterceptor, private val sharedPreferences: SharedPreferences) : ViewModel() {
    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse : StateFlow<LoginResponse?> get() = _loginResponse

    private val _userResponse = MutableStateFlow<User?>(null)
    val userResponse : StateFlow<User?> get() = _userResponse

    private val _refreshTokenResponse = MutableStateFlow<RefreshTokenResponse?>(null)
    val refreshTokenResponse : StateFlow<RefreshTokenResponse?> get() = _refreshTokenResponse

    fun refreshToken(){
        viewModelScope.launch {
            val response = apiService.refreshToken(RefreshTokenRequest(refreshToken = sharedPreferences.getString("refreshToken","")?:""))
            if (response.isSuccessful){
                _refreshTokenResponse.value = response.body()
                sharedPreferences.edit().putString("token",response.body()?.token).apply()
                sharedPreferences.edit().putString("refreshToken",response.body()?.refreshToken).apply()

            }
        }
    }


    fun getCurrentUser(){
        viewModelScope.launch {
            val response = apiService.getCurrentUser()
            if (response.isSuccessful){
                _userResponse.value = response.body()
            }
        }
    }

    fun login(loginRequest: LoginRequest,onSuccess : ()->Unit){
        viewModelScope.launch {
            val response = apiService.login(loginRequest)
            if (response.isSuccessful){
                _loginResponse.value = response.body()
                authInterceptor.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJlbWlseXMiLCJlbWFpbCI6ImVtaWx5LmpvaG5zb25AeC5kdW1teWpzb24uY29tIiwiZmlyc3ROYW1lIjoiRW1pbHkiLCJsYXN0TmFtZSI6IkpvaG5zb24iLCJnZW5kZXIiOiJmZW1hbGUiLCJpbWFnZSI6Imh0dHBzOi8vZHVtbXlqc29uLmNvbS9pY29uL2VtaWx5cy8xMjgiLCJpYXQiOjE3MjQ4NjA2MjEsImV4cCI6MTcyNDg2MjQyMX0.s_dvzrLYxm0ZWEcbqPw23ustGNVGqADPrQDDbtwc3ck")
                sharedPreferences.edit().putString("token",response.body()?.token).apply()
                sharedPreferences.edit().putString("refreshToken",response.body()?.refreshToken).apply()
                onSuccess()
            }
        }
    }


}