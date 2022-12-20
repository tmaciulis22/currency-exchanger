package com.example.currencyexchanger.ui.feature.exchange

import com.example.currencyexchanger.data.model.Balance
import com.example.currencyexchanger.data.model.ConversionResult
import com.example.currencyexchanger.ui.view.exchange.CurrencyInputType

data class ExchangeState(
    val balances: List<Balance> = emptyList(),
    val rates: Map<String, Double> = emptyMap(),
    val conversionResult: ConversionResult? = null,
    val exchangerInputValues: Map<CurrencyInputType, String> = mapOf(
        CurrencyInputType.Sell to "0.00",
        CurrencyInputType.Receive to "0.00"
    ),
    val selectedCurrencies: Map<CurrencyInputType, String> = mapOf()
) {

    val fromAmount
        get() = conversionResult?.from
    val fromCurrency
        get() = selectedCurrencies[CurrencyInputType.Sell]
    val toAmount
        get() = conversionResult?.to
    val toCurrency
        get() = selectedCurrencies[CurrencyInputType.Receive]
    val commissionFee
        get() = conversionResult?.fee
}
