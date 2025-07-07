package com.example.shoppingapp.presentation.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shoppingapp.presentation.home.HomeViewModel
import com.example.shoppingapp.presentation.cart.ProductCard
import com.example.shoppingapp.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val products by viewModel.products.collectAsState()

    val favoriteProducts = products.filter { it.isFavorite }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("المفضلة ❤️") }
            )
        }
    ) { padding ->
        if (favoriteProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("لا توجد منتجات مفضلة حالياً.")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoriteProducts) { product ->
                    ProductCard(
                        product = product,
                        onFavoriteClick = {
                            viewModel.toggleFavorite(product)
                        },
                        onClick = {
                            navController.navigate(Screen.ProductDetails.createRoute(product.id))
                        }
                    )
                }
            }
        }
    }
}