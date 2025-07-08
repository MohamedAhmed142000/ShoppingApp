package com.example.shoppingapp.data.remote.firebase.repository
interface AuthRepository {
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun isLoggedIn(): Boolean
    suspend fun logout()

}
