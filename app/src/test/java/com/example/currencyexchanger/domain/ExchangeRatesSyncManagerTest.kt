package com.example.currencyexchanger.domain

import com.example.currencyexchanger.data.repository.ExchangeRatesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ExchangeRatesSyncManagerTest {

    @Test
    fun testStart(): Unit = runBlocking {
        val mockExchangeRatesRepository = mockk<ExchangeRatesRepository>()
        coEvery { mockExchangeRatesRepository.updateExchangeRates() } returns Unit
        coEvery { mockExchangeRatesRepository.getExchangeRates() } returns flow {  }

        val manager = ExchangeRatesSyncManager(mockExchangeRatesRepository, refreshRate = 5L)

        val job = launch {
            manager.start()
        }
        delay(5L)
        job.cancel()
        coVerify(atLeast = 1) { mockExchangeRatesRepository.updateExchangeRates() }
    }
}
