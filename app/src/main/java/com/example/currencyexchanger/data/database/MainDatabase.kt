package com.example.currencyexchanger.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyexchanger.data.database.dao.BalanceEntityDAO
import com.example.currencyexchanger.data.database.entity.BalanceEntity

@Database(entities = [BalanceEntity::class], version = 1)
abstract class MainDatabase : RoomDatabase() {

    abstract fun balanceEntityDAO(): BalanceEntityDAO
}
