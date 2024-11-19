package com.example.mvvmtemplate.data.model

data class SearchProductsResponse(
    val limit: Int,
    val products: List<ProductX>,
    val skip: Int,
    val total: Int
)