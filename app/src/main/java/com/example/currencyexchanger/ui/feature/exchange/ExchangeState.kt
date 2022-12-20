package com.example.currencyexchanger.ui.feature.exchange

import com.example.currencyexchanger.data.model.Balance
import com.example.currencyexchanger.data.model.ConversionResult

data class ExchangeState(
    val balances: List<Balance> = emptyList(),
    val conversionResult: ConversionResult? = null
)
