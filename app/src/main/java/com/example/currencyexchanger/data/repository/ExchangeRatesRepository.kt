package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.api.endpoint.ExchangeRatesEndpoint
import com.example.currencyexchanger.data.database.dao.ExchangeRatesEntityDAO
import com.example.currencyexchanger.data.database.entity.ExchangeRatesEntity
import com.example.currencyexchanger.data.model.ExchangeRates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class ExchangeRatesRepository(
    private val exchangeRatesEndpoint: ExchangeRatesEndpoint,
    private val exchangeRatesEntityDAO: ExchangeRatesEntityDAO
) {

    fun getExchangeRates(): Flow<ExchangeRates> =
        exchangeRatesEntityDAO.get().mapNotNull { entity ->
            entity?.let {
                ExchangeRates(
                    base = it.base,
                    rates = it.rates
                )
            }
        }

    suspend fun updateExchangeRates(base: String? = null, symbols: List<String>? = null) {
        exchangeRatesEndpoint.getLatestExchangeRates(base, symbols).body()?.let {
            exchangeRatesEntityDAO.insert(
                ExchangeRatesEntity(
                    it.base,
                    it.rates
                )
            )
        }
    }
}
