package com.example.currencyexchanger.data.database.dao

import androidx.room.*
import com.example.currencyexchanger.data.database.entity.ExchangeRatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeRatesEntityDAO {

    @Query("SELECT * FROM exchangeratesentity")
    fun getAll(): Flow<List<ExchangeRatesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exchangeRatesEntities: List<ExchangeRatesEntity>)

    @Delete
    suspend fun delete(exchangeRatesEntity: ExchangeRatesEntity)
}
