package com.example.shoppingapp.presentation.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.domain.model.CartItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel
) {
    val cartItems = viewModel.cartItems.collectAsState().value
    val totalPrice = viewModel.getTotalPrice()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("سلة المشتريات") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("السلة فارغة حالياً", style = MaterialTheme.typography.titleMedium)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cartItems) { product ->
                        CartItem(product = product, onRemove = {
                            viewModel.removeFromCart(product)
                        })
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "الإجمالي: $totalPrice $",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        // لاحقًا: تنفيذ الطلب
                        viewModel.clearCart()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("إتمام الطلب")
                }
            }
        }
    }
}

@Composable
fun CartItem(product: CartItem, onRemove: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.product.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = product.product.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "${product.product.price} $", style = MaterialTheme.typography.bodyMedium)
            }

            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "إزالة من السلة")
            }
        }
    }
}
