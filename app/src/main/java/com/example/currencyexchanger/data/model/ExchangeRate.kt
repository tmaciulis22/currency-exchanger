package com.example.currencyexchanger.data.model

data class ExchangeRate(
    val base: Currency,
    val rates: List<Pair<Currency, Double>>
)
