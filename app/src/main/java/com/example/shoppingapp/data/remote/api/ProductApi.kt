package com.example.shoppingapp.data.remote.api

import com.example.shoppingapp.data.remote.dto.ProductDto
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>
}
