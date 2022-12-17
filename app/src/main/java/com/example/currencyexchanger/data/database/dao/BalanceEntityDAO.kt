package com.example.currencyexchanger.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.currencyexchanger.data.database.entity.BalanceEntity

@Dao
interface BalanceEntityDAO {

    @Query("SELECT * FROM balanceentity")
    suspend fun getAll(): List<BalanceEntity>

    @Insert
    suspend fun insert(balanceEntity: BalanceEntity)

    @Delete
    suspend fun delete(balanceEntity: BalanceEntity)
}
