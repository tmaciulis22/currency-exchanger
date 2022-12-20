package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.api.endpoint.ExchangeRatesEndpoint
import com.example.currencyexchanger.data.database.dao.ExchangeRatesEntityDAO
import com.example.currencyexchanger.data.database.entity.ExchangeRatesEntity
import com.example.currencyexchanger.data.database.entity.RateEntity
import com.example.currencyexchanger.data.model.ExchangeRates
import com.example.currencyexchanger.data.model.Rate

class ExchangeRatesRepository(
    private val exchangeRatesEndpoint: ExchangeRatesEndpoint,
    private val exchangeRatesEntityDAO: ExchangeRatesEntityDAO
) {

    suspend fun getExchangeRates(): ExchangeRates {
        val entity = exchangeRatesEntityDAO.get()

        return ExchangeRates(
            base = entity.base,
            rates = entity.rates.map {
                Rate(it.currency, it.rate)
            }
        )
    }

    suspend fun updateExchangeRates(base: String? = null, symbols: List<String>? = null) {
        exchangeRatesEndpoint.getLatestExchangeRates(base, symbols).body()?.let {
            exchangeRatesEntityDAO.insert(
                ExchangeRatesEntity(
                    it.base,
                    it.rates.map { rate -> RateEntity(rate.currency, rate.rate) }
                )
            )
        }
    }
}
