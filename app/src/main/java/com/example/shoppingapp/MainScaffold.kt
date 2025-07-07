package com.example.shoppingapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.shoppingapp.presentation.navigation.AppNavGraph
import com.example.shoppingapp.presentation.navigation.BottomBar

@Composable
fun MainScaffold(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) {padding->
        AppNavGraph(
            navController = navController, modifier= Modifier.padding(padding)

            )
    }
}
