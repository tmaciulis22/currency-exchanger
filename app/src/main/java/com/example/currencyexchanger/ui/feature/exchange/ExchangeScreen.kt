package com.example.currencyexchanger.ui.feature.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.currencyexchanger.ui.view.balance.BalancesOverview
import com.example.currencyexchanger.ui.view.exchange.CurrencyExchanger

@Composable
fun ExchangeScreen(
    navController: NavController,
    exchangeViewModel: ExchangeViewModel = hiltViewModel()
) {
    val state = exchangeViewModel.balances.collectAsState()

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        BalancesOverview(balances = state.value)
        CurrencyExchanger()
    }
}
