package com.example.shoppingapp.data.remote.mapper

import com.example.shoppingapp.data.remote.dto.ProductDto
import com.example.shoppingapp.domain.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        name = title,
        price = price,
        description=description,
        imageUrl = image,
        category = category
    )
}
