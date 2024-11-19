package com.example.mvvmtemplate.presentation.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class UserViewModel @Inject constructor(private val apiService: ApiService,private val authInterceptor: AuthInterceptor,private val sharedPreferences: SharedPreferences): ViewModel() {
    private val _userResponse = MutableStateFlow<User?>(null)
    val userResponse : StateFlow<User?> get() = _userResponse

    init {

        if (authInterceptor.authToken?.isNotEmpty()==true){
            getCurrentUser()
        }
        else {
            if (sharedPreferences.getString("token","")?.isNotEmpty() == true){
                authInterceptor.setToken(sharedPreferences.getString("token","")?:"")
                getCurrentUser()
            }
        }


    }

    fun getCurrentUser(){
        viewModelScope.launch {
            val response = apiService.getCurrentUser()
            if (response.isSuccessful){
                _userResponse.value = response.body()
            } else if (response.code() == 401){
                refreshToken(){
                    getCurrentUser()
                }

            }
        }
    }

    fun refreshToken(retry :  () ->Unit){
        viewModelScope.launch {
            val response = apiService.refreshToken(RefreshTokenRequest(refreshToken = sharedPreferences.getString("refreshToken","")?:""))
            if (response.isSuccessful){
                sharedPreferences.edit().putString("token",response.body()?.token).apply()
                sharedPreferences.edit().putString("refreshToken",response.body()?.refreshToken).apply()
                authInterceptor.setToken(sharedPreferences.getString("token","")?:"")
                retry()
            }
        }
    }

}