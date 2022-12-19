package com.example.currencyexchanger.domain

import com.example.currencyexchanger.data.model.Currency
import com.example.currencyexchanger.data.repository.ExchangeRatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExchangeRatesSyncManager @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val currencies: List<Currency> = Currency.values().toList(),
    private val refreshRate: Long = REFRESH_RATE_MILLIS
) {

    suspend fun start() {
        coroutineScope {
            flow {
                while (true) {
                    this.emit(Unit)
                    delay(refreshRate)
                }
            }.collectLatest {
                currencies.forEach { currency ->
                    launch(Dispatchers.IO) {
                        exchangeRatesRepository.updateExchangeRates(
                            currency,
                            currencies.filter { it != currency }
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val REFRESH_RATE_MILLIS = 60000L
    }
}
