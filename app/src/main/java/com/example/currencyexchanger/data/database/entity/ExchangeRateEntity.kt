package com.example.currencyexchanger.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExchangeRateEntity(
    @PrimaryKey val base: String,
    val rates: List<Pair<String, Double>>
)

