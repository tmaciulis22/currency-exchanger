package com.example.currencyexchanger.domain

import com.example.currencyexchanger.data.model.ConversionResult
import com.example.currencyexchanger.util.round
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ConversionManager @Inject constructor(
    private val commissionFeeManager: CommissionFeeManager
) {

    private val _conversionResult = MutableStateFlow<ConversionResult?>(null)
    val conversionResult = _conversionResult.asStateFlow()

    fun convert(
        amount: Double,
        fromRate: Double,
        toRate: Double
    ): ConversionResult {
        val rate = toRate / fromRate // Since every rate is based on EUR, we can just divide toRate with fromRate
        val fee = commissionFeeManager.calculateFee(amount, fromRate).round()
        val convertedAmount = ((amount - fee) * rate).round()
        val result = ConversionResult(
            from = amount,
            to = convertedAmount,
            fee = fee
        )

        _conversionResult.value = result

        return result
    }

    fun updateConversionsCount() = commissionFeeManager.updateConversionsCount()
}
