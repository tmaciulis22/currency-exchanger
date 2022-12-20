package com.example.currencyexchanger.data.api.entity

data class ExchangeRatesEntity(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val rates: List<Rate>
)

data class Rate(
    val currency: String,
    val rate: Double
)
