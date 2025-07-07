package com.example.shoppingapp.data.remote.firebase


interface FavoriteRemoteDataSource {
    suspend fun getFavorites(userId: String): List<Int>
    suspend fun addFavorite(userId: String, productId: Int)
    suspend fun removeFavorite(userId: String, productId: Int)
}
