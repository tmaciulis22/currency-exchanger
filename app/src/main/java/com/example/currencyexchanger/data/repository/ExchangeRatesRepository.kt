package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.api.endpoint.ExchangeRatesEndpoint
import com.example.currencyexchanger.data.database.dao.ExchangeRatesEntityDAO
import com.example.currencyexchanger.data.database.entity.ExchangeRateEntity
import com.example.currencyexchanger.data.model.Currency
import com.example.currencyexchanger.data.model.ExchangeRates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExchangeRatesRepository(
    private val exchangeRatesEndpoint: ExchangeRatesEndpoint,
    private val exchangeRatesEntityDAO: ExchangeRatesEntityDAO
) {

    fun getAllExchangeRates(): Flow<List<ExchangeRates>> =
        exchangeRatesEntityDAO
            .getAll()
            .map { entities ->
                entities.map { mapOf(it.base to it.rates) }
            }

    suspend fun updateExchangeRates(base: Currency, symbols: List<Currency>) {
        exchangeRatesEndpoint.getLatestExchangeRates(base, symbols).getOrNull()?.let {
            exchangeRatesEntityDAO.insert(ExchangeRateEntity(it.base, it.rates))
        }
    }
}
