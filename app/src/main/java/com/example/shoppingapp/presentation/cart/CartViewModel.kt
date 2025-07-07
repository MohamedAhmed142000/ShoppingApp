package com.example.shoppingapp.presentation.cart


import androidx.lifecycle.ViewModel
import com.example.shoppingapp.domain.model.CartItem
import com.example.shoppingapp.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addToCart(product: Product) {
        _cartItems.update { currentItems ->
            val existing = currentItems.find { it.product.id == product.id }
            if (existing != null) {
                currentItems.map {
                    if (it.product.id == product.id)
                        it.copy(quantity = it.quantity + 1)
                    else it
                }
            } else {
                currentItems + CartItem(product, 1)
            }
        }
    }

    fun removeFromCart(productId: Int) {
        _cartItems.update { items ->
            items.filterNot { it.product.id == productId }
        }
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.sumOf { it.totalPrice }
    }
}
