package com.example.currencyexchanger.domain

import com.example.currencyexchanger.data.model.ConversionResult
import com.example.currencyexchanger.util.round
import javax.inject.Inject

class ConversionManager @Inject constructor(
    private val commissionFeeManager: CommissionFeeManager
) {

    fun convert(
        amount: Double,
        fromRate: Double,
        toRate: Double
    ): ConversionResult {
        val rate = toRate / fromRate // Since every rate is based on EUR, we can just divide toRate with fromRate
        val fee = commissionFeeManager.calculateFee(amount, fromRate).round()
        val convertedAmount = ((amount - fee) * rate).round()

        return ConversionResult(
            from = amount,
            to = convertedAmount,
            fee = fee
        )
    }

    fun updateConversionsCount() = commissionFeeManager.updateConversionsCount()
}
