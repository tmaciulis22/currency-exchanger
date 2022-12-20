package com.example.currencyexchanger.domain

import com.example.currencyexchanger.data.repository.ExchangeRatesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExchangeRatesSyncManager @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val refreshRate: Long = REFRESH_RATE_MILLIS
) {

    val exchangeRates = exchangeRatesRepository.getExchangeRates()

    suspend fun start() {
        flow {
            while (true) {
                this.emit(Unit)
                delay(refreshRate)
            }
        }.collectLatest {
            exchangeRatesRepository.updateExchangeRates()
        }
    }

    companion object {
        private const val REFRESH_RATE_MILLIS = 60000L
    }
}
