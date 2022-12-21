package com.example.currencyexchanger.ui.feature.exchange

import com.example.currencyexchanger.ui.view.exchange.CurrencyInputType

data class ExchangeUIState(
    val exchangerInputValues: Map<CurrencyInputType, String> = mapOf(
        CurrencyInputType.Sell to "0.00",
        CurrencyInputType.Receive to "0.00"
    ),
    val selectedCurrencies: Map<CurrencyInputType, String> = mapOf(),
    val showSuccessDialog: Boolean = false,
    val errorMessage: String? = null
) {

    val sellAmount
        get() = exchangerInputValues[CurrencyInputType.Sell]

    val fromCurrency
        get() = selectedCurrencies[CurrencyInputType.Sell]
    val toCurrency
        get() = selectedCurrencies[CurrencyInputType.Receive]
}
