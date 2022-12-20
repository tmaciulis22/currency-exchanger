package com.example.currencyexchanger.domain

import com.example.currencyexchanger.data.repository.ExchangeRatesRepository
import javax.inject.Inject

class ConversionManager @Inject constructor(
    private val commissionFeeManager: CommissionFeeManager,
    private val exchangeRatesRepository: ExchangeRatesRepository
) {

    suspend fun convert(
        amount: Double,
        fromCurrency: String,
        toCurrency: String
    ) {
        val rate = exchangeRatesRepository
            .getExchangeRate(fromCurrency)
            .rates
            .filter { it.first == toCurrency }

        commissionFeeManager.calculateFee()
    }
}
