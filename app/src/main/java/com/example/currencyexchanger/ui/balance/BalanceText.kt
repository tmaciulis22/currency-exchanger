package com.example.currencyexchanger.ui.balance

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun BalanceText(amount: Int, currency: String) {
    Text("$amount $currency")
}
