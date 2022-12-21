package com.example.currencyexchanger.data.database.dao

import androidx.room.*
import com.example.currencyexchanger.data.database.entity.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceEntityDAO {

    @Query("SELECT * FROM balanceentity")
    fun getAll(): Flow<List<BalanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(balanceEntities: List<BalanceEntity>)

    @Delete
    suspend fun delete(balanceEntity: BalanceEntity)
}
