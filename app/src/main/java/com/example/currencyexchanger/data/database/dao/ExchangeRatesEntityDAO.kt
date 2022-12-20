package com.example.currencyexchanger.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyexchanger.data.database.entity.ExchangeRatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeRatesEntityDAO {

    @Query("SELECT * FROM exchangeratesentity WHERE base = :base")
    fun get(base: String = "EUR"): Flow<ExchangeRatesEntity?> // Since we save all rates from EUR to X currency, we use EUR as default base parameter here

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exchangeRatesEntities: ExchangeRatesEntity)
}
