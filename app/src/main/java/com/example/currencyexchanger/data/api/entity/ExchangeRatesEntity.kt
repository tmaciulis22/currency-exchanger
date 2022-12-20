package com.example.currencyexchanger.data.api.entity

data class ExchangeRatesEntity(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
