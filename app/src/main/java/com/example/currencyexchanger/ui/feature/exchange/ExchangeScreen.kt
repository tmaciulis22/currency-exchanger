package com.example.currencyexchanger.ui.feature.exchange

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.currencyexchanger.ui.view.balance.BalancesOverview

@Composable
fun ExchangeScreen(
    navController: NavController,
    exchangeViewModel: ExchangeViewModel = hiltViewModel()
) {
    val state = exchangeViewModel.balances.collectAsState()

    BalancesOverview(balances = state.value)
}
