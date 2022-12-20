package com.example.currencyexchanger.data.model

data class Balance(
    val amount: Double = DEFAULT_AMOUNT,
    val currency: String = DEFAULT_CURRENCY
) {
    companion object {
        private const val DEFAULT_AMOUNT = 1000.0
        private const val DEFAULT_CURRENCY = "EUR"
    }
}
