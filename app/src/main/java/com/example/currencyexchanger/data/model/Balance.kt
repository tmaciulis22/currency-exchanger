package com.example.currencyexchanger.data.model

data class Balance(
    val amount: Double = DEFAULT_AMOUNT,
    val currency: Currency = DEFAULT_CURRENCY
) {
    companion object {
        const val DEFAULT_AMOUNT = 1000.0
        val DEFAULT_CURRENCY = Currency.EUR
    }
}
