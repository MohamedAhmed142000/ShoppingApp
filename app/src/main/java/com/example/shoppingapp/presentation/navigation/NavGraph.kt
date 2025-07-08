package com.example.shoppingapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shoppingapp.MainScaffold
import com.example.shoppingapp.presentation.cart.CartScreen
import com.example.shoppingapp.presentation.cart.CartViewModel
import com.example.shoppingapp.presentation.favorite.FavoriteScreen
import com.example.shoppingapp.presentation.home.HomeScreen
import com.example.shoppingapp.presentation.login.LoginScreen
import com.example.shoppingapp.presentation.product_details.ProductDetailsScreen
import com.example.shoppingapp.presentation.register.RegisterScreen
import com.example.shoppingapp.presentation.splash.SplashScreen

// تعريف مسارات التنقل
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ProductDetails : Screen("details/{productId}") {
        fun createRoute(productId: Int): String = "details/$productId"
    }

    object Cart : Screen("cart")
    object Favorite : Screen("favorite")
    object Login : Screen("login")
    object Register : Screen("register")
    object Splash : Screen("splash")
}

// NavGraph الرئيسي
@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val cartViewModel: CartViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Home.route) {
            MainScaffold(navController,cartViewModel) {
                HomeScreen(navController)
            }
        }
        composable(Screen.Favorite.route) {
            MainScaffold(navController,cartViewModel) {
                FavoriteScreen(navController)
            }
        }
        composable(Screen.Cart.route) {
            MainScaffold(navController,cartViewModel) {
                CartScreen(navController, cartViewModel)
            }
        }

        // شاشة تفاصيل المنتج خارج BottomBar
        composable(
            route = Screen.ProductDetails.route,
            arguments = listOf(navArgument("productId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: -1
            ProductDetailsScreen(
                productId = productId,
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
    }
}
