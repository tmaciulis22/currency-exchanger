package com.example.currencyexchanger.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
data class ExchangeRatesEntity(
    @PrimaryKey val base: String,
    val rates: List<RateEntity>
)

data class RateEntity(
    val currency: String,
    val rate: Double
)
