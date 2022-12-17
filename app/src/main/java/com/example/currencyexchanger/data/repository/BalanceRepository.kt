package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.database.dao.BalanceEntityDAO
import com.example.currencyexchanger.data.model.Balance

class BalanceRepository(
    private val balanceEntityDAO: BalanceEntityDAO
) {

    suspend fun getAllBalances(): List<Balance> =
        balanceEntityDAO.getAll().map {
            Balance(
                amount = it.amount,
                currency = it.currency
            )
        }
}
