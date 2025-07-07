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
            repository.toggleFavorite(product)
            getProducts() // Reload updated list
        }
    }
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}
