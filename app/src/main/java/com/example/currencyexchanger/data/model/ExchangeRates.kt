package com.example.currencyexchanger.data.model

data class ExchangeRates(
    val base: String,
    val rates: List<Pair<String, Double>>
)
