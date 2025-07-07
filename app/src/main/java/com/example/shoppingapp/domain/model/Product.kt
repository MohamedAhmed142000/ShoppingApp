package com.example.shoppingapp.domain.model
data class Product(
    val id: Int,
    val name: String,
    val description:String,
    val imageUrl: String,
    val price: Double,
    val category: String,
    val isFavorite: Boolean = false
)
