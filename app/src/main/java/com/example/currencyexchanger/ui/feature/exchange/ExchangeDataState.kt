package com.example.currencyexchanger.ui.feature.exchange

import com.example.currencyexchanger.data.model.Balance
import com.example.currencyexchanger.data.model.ConversionResult
import com.example.currencyexchanger.ui.view.exchange.CurrencyInputType

data class ExchangeDataState(
    val balances: List<Balance> = emptyList(),
    val rates: Map<String, Double> = emptyMap(),
    val conversionResult: ConversionResult? = null,
) {

    val fromAmount
        get() = conversionResult?.from
    val toAmount
        get() = conversionResult?.to
    val commissionFee
        get() = conversionResult?.fee
}
