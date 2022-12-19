package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.api.endpoint.ExchangeRatesEndpoint
import com.example.currencyexchanger.data.model.Currency
import com.example.currencyexchanger.data.model.ExchangeRates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExchangeRatesRepository(
    private val exchangeRatesEndpoint: ExchangeRatesEndpoint
) {

    fun getExchangeRates(base: Currency, symbols: List<Currency>): Flow<ExchangeRates> = flow {
        val rates = exchangeRatesEndpoint.getLatestExchangeRates(base, symbols)
        rates.getOrNull()?.let {
            emit(ExchangeRates(it.base, it.rates))
        }
    }
}
