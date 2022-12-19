package com.example.currencyexchanger.di

import android.content.Context
import androidx.room.Room
import com.example.currencyexchanger.data.database.MainDatabase
import com.example.currencyexchanger.data.database.dao.BalanceEntityDAO
import com.example.currencyexchanger.data.repository.BalanceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMainDatabase(@ApplicationContext appContext: Context): MainDatabase {
        return Room.databaseBuilder(
            appContext,
            MainDatabase::class.java,
            "exchanger-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBalanceEntityDAO(mainDatabase: MainDatabase): BalanceEntityDAO {
        return mainDatabase.balanceEntityDAO()
    }

    @Provides
    @Singleton
    fun provideBalanceRepository(balanceEntityDAO: BalanceEntityDAO): BalanceRepository {
        return BalanceRepository(balanceEntityDAO)
    }
}
