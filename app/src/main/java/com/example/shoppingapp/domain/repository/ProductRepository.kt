package com.example.shoppingapp.domain.repository


import com.example.shoppingapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    suspend fun toggleFavorite(product: Product)
}
