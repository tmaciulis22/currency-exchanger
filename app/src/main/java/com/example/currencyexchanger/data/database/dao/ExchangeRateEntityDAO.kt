package com.example.currencyexchanger.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyexchanger.data.database.entity.ExchangeRateEntity
import com.example.currencyexchanger.data.model.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeRateEntityDAO {

    @Query("SELECT * FROM exchangerateentity")
    fun getAll(): Flow<List<ExchangeRateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exchangeRatesEntities: ExchangeRateEntity)

    @Delete
    suspend fun delete(exchangeRateEntity: ExchangeRateEntity)
}
