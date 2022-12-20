package com.example.currencyexchanger.data.api.entity

data class ExchangeRateEntity(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val rates: List<Pair<String, Double>>
)
