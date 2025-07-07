package com.example.shoppingapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shoppingapp.presentation.cart.CartScreen
import com.example.shoppingapp.presentation.home.HomeScreen
import com.example.shoppingapp.presentation.product_details.ProductDetailsScreen

// تعريف مسارات التنقل
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ProductDetails : Screen("details/{productId}") {
        fun createRoute(productId: Int): String = "details/$productId"
    }
    object Cart : Screen("cart")
}

// NavGraph الرئيسي
@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // شاشة الرئيسية
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        // شاشة تفاصيل المنتج
        composable(
            route = Screen.ProductDetails.route,
            arguments = listOf(navArgument("productId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: -1
            ProductDetailsScreen(productId = productId, navController = navController)
        }

        // شاشة السلة
        composable(Screen.Cart.route) {
            CartScreen()
        }
    }
}
