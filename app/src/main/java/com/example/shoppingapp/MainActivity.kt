package com.example.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.presentation.cart.CartViewModel
import com.example.shoppingapp.presentation.navigation.AppNavGraph
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingAppTheme {
                val cartViewModel: CartViewModel = hiltViewModel()

                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}

