package com.example.currencyexchanger.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.currencyexchanger.data.database.entity.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceEntityDAO {

    @Query("SELECT * FROM balanceentity")
    suspend fun getAll(): Flow<List<BalanceEntity>>

    @Insert
    suspend fun insert(balanceEntity: BalanceEntity)

    @Delete
    suspend fun delete(balanceEntity: BalanceEntity)
}
