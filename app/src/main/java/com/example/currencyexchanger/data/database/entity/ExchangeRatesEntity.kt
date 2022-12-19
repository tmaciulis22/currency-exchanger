package com.example.currencyexchanger.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.currencyexchanger.data.model.Currency

@Entity
data class ExchangeRatesEntity(
    @PrimaryKey val base: Currency,
    val rates: List<Pair<Currency, Double>>
)

