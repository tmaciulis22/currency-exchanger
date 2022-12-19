package com.example.currencyexchanger.data.model

data class ExchangeRates(
    val base: Currency,
    val rates: List<Pair<Currency, Double>>
)
