package com.example.currencyexchanger.domain

import com.example.currencyexchanger.data.model.Balance
import com.example.currencyexchanger.data.model.ConversionResult
import com.example.currencyexchanger.data.repository.BalanceRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BalancesManagerTest {

    private val conversionResultToTest = ConversionResult(from = 25.0, to = 27.0, fee = 3.0)
    private val fromCurrencyToTest = "EUR"
    private val toCurrencyToTest = "USD"

    private val defaultBalance = Balance()
    private val oneEurBalance = Balance(amount = 1.0)
    private val usdBalance = Balance(currency = "USD")
    private val newToBalance = Balance(amount = 972.0, currency = "EUR")
    private val newFromBalance = Balance(amount = 27.0, currency = "USD")

    @Test
    fun test_updateBalances(): Unit = runBlocking {
        val mockRepository = mockk<BalanceRepository>()

        every { mockRepository.getAllBalances() } returns flow { emit(listOf(defaultBalance)) }
        coEvery { mockRepository.insertBalances(listOf(newToBalance, newFromBalance)) } returns Unit

        val manager = BalancesManager(mockRepository)

        val result = manager.updateBalances(conversionResultToTest, fromCurrencyToTest, toCurrencyToTest)
        assertTrue(result.isSuccess)
    }

    @Test
    fun test_updateBalances_notEnoughMoney(): Unit = runBlocking {
        val mockRepository = mockk<BalanceRepository>()

        every { mockRepository.getAllBalances() } returns flow { emit(listOf(oneEurBalance)) }

        val manager = BalancesManager(mockRepository)

        val result = manager.updateBalances(conversionResultToTest, fromCurrencyToTest, toCurrencyToTest)
        assertTrue(result.isFailure)
    }

    @Test
    fun test_updateBalances_couldNotGetBalances(): Unit = runBlocking {
        val mockRepository = mockk<BalanceRepository>()

        every { mockRepository.getAllBalances() } returns flow { }

        val manager = BalancesManager(mockRepository)

        val result = manager.updateBalances(conversionResultToTest, fromCurrencyToTest, toCurrencyToTest)
        assertTrue(result.isFailure)
    }

    @Test
    fun test_updateBalances_couldNotFindFromBalance(): Unit = runBlocking {
        val mockRepository = mockk<BalanceRepository>()

        every { mockRepository.getAllBalances() } returns flow { emit(listOf(usdBalance)) }

        val manager = BalancesManager(mockRepository)

        val result = manager.updateBalances(conversionResultToTest, fromCurrencyToTest, toCurrencyToTest)
        assertTrue(result.isFailure)
    }
}
