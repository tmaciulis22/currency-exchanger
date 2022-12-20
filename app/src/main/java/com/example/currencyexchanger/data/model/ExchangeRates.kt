package com.example.currencyexchanger.data.model

data class ExchangeRates(
    val base: String,
    val rates: List<Rate>
)

data class Rate(
    val currency: String,
    val rate: Double
)
