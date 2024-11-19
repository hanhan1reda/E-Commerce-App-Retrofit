package com.example.mvvmtemplate.data.network

import com.example.mvvmtemplate.data.model.GetAllProductsResponse
import com.example.mvvmtemplate.data.model.LoginRequest
import com.example.mvvmtemplate.data.model.LoginResponse
import com.example.mvvmtemplate.data.model.Product
import com.example.mvvmtemplate.data.model.RefreshTokenRequest
import com.example.mvvmtemplate.data.model.RefreshTokenResponse
import com.example.mvvmtemplate.data.model.SearchProductsResponse
import com.example.mvvmtemplate.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // implement Network Functions here
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @GET("auth/me")
    suspend fun getCurrentUser() : Response<User>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest) : Response<RefreshTokenResponse>

    @GET("products")
    suspend fun getAllProducts() : Response<GetAllProductsResponse>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id:Int) : Response<Product>

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query : String) : Response<SearchProductsResponse>
}