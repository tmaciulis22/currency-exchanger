package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.api.endpoint.ExchangeRatesEndpoint
import com.example.currencyexchanger.data.database.dao.ExchangeRateEntityDAO
import com.example.currencyexchanger.data.database.entity.ExchangeRatesEntity
import com.example.currencyexchanger.data.model.ExchangeRates

class ExchangeRatesRepository(
    private val exchangeRatesEndpoint: ExchangeRatesEndpoint,
    private val exchangeRatesEntityDAO: ExchangeRateEntityDAO
) {

    suspend fun getExchangeRates(): ExchangeRates {
        val entity = exchangeRatesEntityDAO.get()

        return ExchangeRates(
            base = entity.base,
            rates = entity.rates
        )
    }

    suspend fun updateExchangeRates(base: String? = null, symbols: List<String>? = null) {
        exchangeRatesEndpoint.getLatestExchangeRates(base, symbols).getOrNull()?.let {
            exchangeRatesEntityDAO.insert(ExchangeRatesEntity(it.base, it.rates))
        }
    }
}
