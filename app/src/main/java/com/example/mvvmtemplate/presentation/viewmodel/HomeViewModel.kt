package com.example.mvvmtemplate.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtemplate.data.model.GetAllProductsResponse
import com.example.mvvmtemplate.data.model.Product
import com.example.mvvmtemplate.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _productsResponse = MutableStateFlow<GetAllProductsResponse?>(null)
    val productsResponse : StateFlow<GetAllProductsResponse?> get() = _productsResponse

    private val _product = MutableStateFlow<Product?>(null)
    val product : StateFlow<Product?> get() = _product

    init {
        getProducts()
    }

    fun getProductById(id : Int){
        viewModelScope.launch {
            val response = apiService.getProductById(id)
            if(response.isSuccessful){
                _product.value = response.body()
            }
        }
    }

    fun getProducts(){
        viewModelScope.launch {
            val response = apiService.getAllProducts()
            if(response.isSuccessful){
                _productsResponse.value = response.body()
            }
        }
    }
}