package com.example.shoppingapp.data.remote.firebase


import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoriteRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FavoriteRemoteDataSource {

    override suspend fun getFavorites(userId: String): List<Int> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .get()
                .await()

            snapshot.documents.mapNotNull {
                it.getLong("id")?.toInt()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addFavorite(userId: String, productId: Int) {
        firestore.collection("users")
            .document(userId)
            .collection("favorites")
            .document(productId.toString())
            .set(mapOf("id" to productId))
            .await()
    }

    override suspend fun removeFavorite(userId: String, productId: Int) {
        firestore.collection("users")
            .document(userId)
            .collection("favorites")
            .document(productId.toString())
            .delete()
            .await()
    }
}
