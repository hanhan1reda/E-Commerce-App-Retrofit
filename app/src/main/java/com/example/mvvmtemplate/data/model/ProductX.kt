package com.example.mvvmtemplate.data.model

data class ProductX(
    val availabilityStatus: String,
    val brand: String,
    val category: String,
    val description: String,
    val dimensions: DimensionsX,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val meta: MetaX,
    val minimumOrderQuantity: Int,
    val price: Double,
    val rating: Double,
    val returnPolicy: String,
    val reviews: List<ReviewX>,
    val shippingInformation: String,
    val sku: String,
    val stock: Int,
    val tags: List<String>,
    val thumbnail: String,
    val title: String,
    val warrantyInformation: String,
    val weight: Int
)