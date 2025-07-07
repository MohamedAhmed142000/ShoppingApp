package com.example.shoppingapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.model.Product
import com.example.shoppingapp.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    val products: StateFlow<List<Product>> = combine(
        _allProducts, _searchQuery
    ) { allProducts, query ->
        if (query.isBlank()) {
            allProducts
        } else {
            allProducts.filter {
                it.name.contains(query.trim(), ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            repository.getAllProducts()
                .catch { e -> /* Handle error */ }
                .collect { result ->
                    _allProducts.value = result
                }
            }
        }


    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            // 1. اعكس الحالة مباشرة
            val updatedProduct = product.copy(isFavorite = !product.isFavorite)

            // 2. حدث القائمة فورًا في الواجهة
            _allProducts.update { currentList ->
                currentList.map {
                    if (it.id == product.id) updatedProduct else it
                }
            }

            // 3. نفّذ الطلب في الخلفية (Firebase)
            try {
                if (updatedProduct.isFavorite) {
                    repository.addFavorite(product.id)
                } else {
                    repository.removeFavorite(product.id)
                }
            } catch (e: Exception) {
                // في حالة فشل، ارجع للحالة الأصلية
                _allProducts.update { currentList ->
                    currentList.map {
                        if (it.id == product.id) product else it
                    }
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}
