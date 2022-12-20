package com.example.currencyexchanger.domain

import com.example.currencyexchanger.data.model.Balance
import com.example.currencyexchanger.data.model.ConversionResult
import com.example.currencyexchanger.data.repository.BalanceRepository
import com.example.currencyexchanger.util.round
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class BalancesManager @Inject constructor(
    private val balanceRepository: BalanceRepository
) {
    val balances = balanceRepository.getAllBalances()

    suspend fun updateBalances(
        conversionResult: ConversionResult,
        fromCurrency: String,
        toCurrency: String
    ): Result<Unit> {
        val balances = balances.firstOrNull() ?: emptyList()
        val fromBalance = balances.firstOrNull { it.currency == fromCurrency }
            ?: Balance(amount = 0.0, currency = fromCurrency)
        val toBalance = balances.firstOrNull { it.currency == toCurrency }
            ?: Balance(amount = 0.0, currency = toCurrency)

        if (fromBalance.amount - conversionResult.amountWithFee < 0) {
            return Result.failure(Error("Not enough money in balance"))
        }

        val newFromBalance = Balance(
            amount = (fromBalance.amount - conversionResult.amountWithFee).round(),
            currency = fromBalance.currency
        )
        val newToBalance = Balance(
            amount = (toBalance.amount + conversionResult.to).round(),
            currency = toBalance.currency
        )

        balanceRepository.insertBalances(listOf(newFromBalance, newToBalance))

        return Result.success(Unit)
    }
}
