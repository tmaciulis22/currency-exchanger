package com.example.currencyexchanger.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BalanceEntity(
    @PrimaryKey val currency: String,
    val amount: Double
)
