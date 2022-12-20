package com.example.currencyexchanger.domain

import com.example.currencyexchanger.data.model.ConversionResult
import com.example.currencyexchanger.data.repository.ExchangeRatesRepository
import javax.inject.Inject

class ConversionManager @Inject constructor(
    private val commissionFeeManager: CommissionFeeManager,
    private val exchangeRatesRepository: ExchangeRatesRepository,
) {

    suspend fun convert(
        amount: Double,
        fromCurrency: String,
        toCurrency: String
    ): ConversionResult {
        val rates = exchangeRatesRepository.getExchangeRates().rates
        val fromRate = rates.firstOrNull { it.currency == fromCurrency }?.rate ?: 1.0
        val toRate = rates.firstOrNull { it.currency == toCurrency }?.rate ?: 1.0
        val rate = toRate / fromRate

        val fee = commissionFeeManager.calculateFee(amount, fromRate)
        val convertedAmount = (amount - fee) * rate

        return ConversionResult(
            from = amount,
            to = convertedAmount,
            fee = fee
        )
    }
}
