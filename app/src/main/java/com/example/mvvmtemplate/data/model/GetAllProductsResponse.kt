package com.example.mvvmtemplate.data.model

data class GetAllProductsResponse(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)