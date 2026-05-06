package com.example.prestamoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prestamoapp.presentation.input.InputScreen
import com.example.prestamoapp.presentation.result.ResultScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "input"
    ) {
        composable("input") {
            InputScreen(navController)
        }

        composable("result/{cuota}/{interes}/{monto}") { backStackEntry ->
            val cuota = backStackEntry.arguments?.getString("cuota")?.toDouble() ?: 0.0
            val interes = backStackEntry.arguments?.getString("interes")?.toDouble() ?: 0.0
            val monto = backStackEntry.arguments?.getString("monto")?.toDouble() ?: 0.0

            ResultScreen(
                cuota = cuota,
                interes = interes,
                monto = monto,
                navController = navController
            )
        }
    }
}