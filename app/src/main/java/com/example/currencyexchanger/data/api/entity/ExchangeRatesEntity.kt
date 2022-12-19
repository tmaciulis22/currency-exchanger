package com.example.currencyexchanger.data.api.entity

import com.example.currencyexchanger.data.model.Currency

data class ExchangeRatesEntity(
    val base: Currency,
    val rates: List<Pair<Currency, Double>>
)
