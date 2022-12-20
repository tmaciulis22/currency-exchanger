package com.example.currencyexchanger.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExchangeRatesEntity(
    @PrimaryKey val base: String,
    val rates: Map<String, Double>
)
