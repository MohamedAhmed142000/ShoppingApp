package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.remote.api.ProductApi
import com.example.shoppingapp.data.remote.firebase.FavoriteRemoteDataSource
import com.example.shoppingapp.data.remote.mapper.toProduct
import com.example.shoppingapp.domain.model.Product
import com.example.shoppingapp.domain.repository.ProductRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi,
    private val favoriteDataSource: FavoriteRemoteDataSource
) : ProductRepository {

    private val userId = "default_user" // مؤقتًا (هنستخدم Firebase Auth لاحقًا)

    override fun getAllProducts(): Flow<List<Product>> = flow {
        val response = api.getProducts()
        val products = response.map { it.toProduct() }

        val favoriteIds = favoriteDataSource.getFavorites(userId)

        val updated = products.map { product ->
            product.copy(isFavorite = favoriteIds.contains(product.id))
        }

        emit(updated)
    }

    override suspend fun addFavorite(productId: Int) {
        favoriteDataSource.addFavorite(userId, productId)
    }

    override suspend fun removeFavorite(productId: Int) {
        favoriteDataSource.removeFavorite(userId, productId)
    }
}

