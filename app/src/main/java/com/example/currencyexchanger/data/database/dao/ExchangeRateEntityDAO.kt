package com.example.currencyexchanger.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyexchanger.data.database.entity.ExchangeRatesEntity

@Dao
interface ExchangeRateEntityDAO {

    @Query("SELECT * FROM exchangeratesentity WHERE base = :base")
    suspend fun get(base: String = "EUR"): ExchangeRatesEntity // Since we save all rates from EUR to X currency, we use EUR as default base parameter here

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exchangeRatesEntities: ExchangeRatesEntity)

    @Delete
    suspend fun delete(exchangeRatesEntity: ExchangeRatesEntity)
}
