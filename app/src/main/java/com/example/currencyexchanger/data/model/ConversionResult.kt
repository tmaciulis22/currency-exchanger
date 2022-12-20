package com.example.currencyexchanger.data.model

data class ConversionResult(
    val from: Double,
    val to: Double,
    val fee: Double
) {
    val totalFromAmount: Double
        get() = from + fee
}
