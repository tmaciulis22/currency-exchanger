package com.example.currencyexchanger.data.model

data class ExchangeRate(
    val base: String,
    val rates: List<Pair<String, Double>>
)
