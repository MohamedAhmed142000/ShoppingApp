package com.example.shoppingapp.presentation.product_details
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.domain.model.Product
import com.example.shoppingapp.presentation.cart.CartViewModel
import com.example.shoppingapp.presentation.home.HomeViewModel
import com.example.shoppingapp.presentation.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    productId: Int,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    cartViewModel: CartViewModel // لازم يتبعت من MainActivity أو NavGraph
) {
    val product = homeViewModel.products.collectAsState().value.find { it.id == productId }

    product?.let { item ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(item.name) },
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(Screen.Cart.route)
                        }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "السلة")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // صورة + زر المفضلة
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(item.imageUrl),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    IconButton(
                        onClick = { homeViewModel.toggleFavorite(item) },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = if (item.isFavorite)
                                Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "مفضلة",
                            tint = Color.Red
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = item.name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${item.price} $", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "الفئة: ${item.category}", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "وصف المنتج:\n${item.description}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { cartViewModel.addToCart(item) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("أضف إلى السلة")
                }
            }
        }
    } ?: run {
        // المنتج مش موجود
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("المنتج غير موجود", style = MaterialTheme.typography.titleMedium)
        }
    }
}