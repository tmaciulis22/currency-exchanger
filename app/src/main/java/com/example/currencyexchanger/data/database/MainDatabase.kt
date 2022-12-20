package com.example.currencyexchanger.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.currencyexchanger.data.database.dao.BalanceEntityDAO
import com.example.currencyexchanger.data.database.dao.ExchangeRatesEntityDAO
import com.example.currencyexchanger.data.database.entity.BalanceEntity
import com.example.currencyexchanger.data.database.entity.ExchangeRatesEntity

@Database(
    entities = [
        BalanceEntity::class,
        ExchangeRatesEntity::class
    ],
    version = 1
)
@TypeConverters(ListRateEntityTypeConverter::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun balanceEntityDAO(): BalanceEntityDAO

    abstract fun exchangeRatesEntityDAO(): ExchangeRatesEntityDAO
}
