package com.example.currencyexchanger.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BalanceEntity(
    @PrimaryKey val currency: String = DEFAULT_CURRENCY,
    val amount: Double = DEFAULT_AMOUNT
) {
    companion object {
        private const val DEFAULT_AMOUNT = 1000.0
        private const val DEFAULT_CURRENCY = "EUR"
    }
}
