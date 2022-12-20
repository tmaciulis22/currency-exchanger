package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.api.endpoint.ExchangeRatesEndpoint
import com.example.currencyexchanger.data.database.dao.ExchangeRateEntityDAO
import com.example.currencyexchanger.data.database.entity.ExchangeRateEntity
import com.example.currencyexchanger.data.model.ExchangeRate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExchangeRatesRepository(
    private val exchangeRatesEndpoint: ExchangeRatesEndpoint,
    private val exchangeRatesEntityDAO: ExchangeRateEntityDAO
) {

    fun getAllExchangeRates(): Flow<List<ExchangeRate>> =
        exchangeRatesEntityDAO
            .getAll()
            .map { entities ->
                entities.map { ExchangeRate(it.base, it.rates) }
            }

    suspend fun getExchangeRate(base: String): ExchangeRate {
        val entity = exchangeRatesEntityDAO.get(base)

        return ExchangeRate(
            base = entity.base,
            rates = entity.rates
        )
    }

    suspend fun updateExchangeRates(base: String? = null, symbols: List<String>? = null) {
        exchangeRatesEndpoint.getLatestExchangeRate(base, symbols).getOrNull()?.let {
            exchangeRatesEntityDAO.insert(ExchangeRateEntity(it.base, it.rates))
        }
    }
}
