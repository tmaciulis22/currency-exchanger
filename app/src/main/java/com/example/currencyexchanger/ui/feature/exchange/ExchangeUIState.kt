package com.example.currencyexchanger.ui.feature.exchange

import com.example.currencyexchanger.ui.view.exchange.CurrencyInputType

data class ExchangeUIState(
    val isLoading: Boolean = false,
    val exchangerInputValues: Map<CurrencyInputType, String> = mapOf(
        CurrencyInputType.Sell to "0.00",
        CurrencyInputType.Receive to "0.00"
    ),
    val selectedCurrencies: Map<CurrencyInputType, String> = mapOf(),
    val showSuccessDialog: Boolean = false
) {

    val fromCurrency
        get() = selectedCurrencies[CurrencyInputType.Sell]
    val toCurrency
        get() = selectedCurrencies[CurrencyInputType.Receive]
}
