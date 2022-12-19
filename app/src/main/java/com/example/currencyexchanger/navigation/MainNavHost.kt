package com.example.currencyexchanger.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.currencyexchanger.ui.feature.exchange.ExchangeScreen

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.Exchange.name) {
        composable(Route.Exchange.name) {
            ExchangeScreen(navController)
        }
    }
}
