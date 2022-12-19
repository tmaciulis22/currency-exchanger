package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.database.dao.BalanceEntityDAO
import com.example.currencyexchanger.data.database.entity.BalanceEntity
import com.example.currencyexchanger.data.model.Balance
import kotlinx.coroutines.flow.*

class BalanceRepository(
    private val balanceEntityDAO: BalanceEntityDAO
) {

    fun getAllBalances(): Flow<List<Balance>> =
        balanceEntityDAO
            .getAll()
            .map { entities ->
                if (entities.isNotEmpty()) {
                    entities.map {
                        Balance(
                            amount = it.amount,
                            currency = it.currency
                        )
                    }
                } else {
                    listOf(Balance())
                }
            }

    suspend fun insertBalance(balance: Balance) = balanceEntityDAO.insert(
        BalanceEntity(
            balance.currency,
            balance.amount
        )
    )

    suspend fun deleteBalance(balance: Balance) = balanceEntityDAO.delete(
        BalanceEntity(
            balance.currency,
            balance.amount
        )
    )
}
