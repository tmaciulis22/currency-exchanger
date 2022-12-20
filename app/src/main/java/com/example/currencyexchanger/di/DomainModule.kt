package com.example.currencyexchanger.di

import android.content.SharedPreferences
import com.example.currencyexchanger.data.repository.BalanceRepository
import com.example.currencyexchanger.data.repository.ExchangeRatesRepository
import com.example.currencyexchanger.domain.BalancesManager
import com.example.currencyexchanger.domain.CommissionFeeManager
import com.example.currencyexchanger.domain.ConversionManager
import com.example.currencyexchanger.domain.ExchangeRatesSyncManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun provideExchangeRatesSyncManager(
        exchangeRatesRepository: ExchangeRatesRepository
    ): ExchangeRatesSyncManager {
        return ExchangeRatesSyncManager(exchangeRatesRepository)
    }

    @Provides
    @Singleton
    fun provideCommissionFeeManager(
        sharedPreferences: SharedPreferences
    ): CommissionFeeManager {
        return CommissionFeeManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideConversionManager(
        commissionFeeManager: CommissionFeeManager
    ): ConversionManager {
        return ConversionManager(commissionFeeManager)
    }

    @Provides
    @Singleton
    fun provideBalancesManager(
        balanceRepository: BalanceRepository
    ): BalancesManager {
        return BalancesManager(balanceRepository)
    }
}
