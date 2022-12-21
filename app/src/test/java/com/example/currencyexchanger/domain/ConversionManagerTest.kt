package com.example.currencyexchanger.domain

import com.example.currencyexchanger.data.model.ConversionResult
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class ConversionManagerTest {

    private val testAmount = 500.0
    private val testFromRate = 2.5
    private val testToRate = 5.0
    private val expectedConvertedAmount = (testAmount - 5.0) * (testToRate / testFromRate)
    private val expectedFee = 5.0
    private val expectedConversionResult = ConversionResult(
        from = testAmount,
        to = expectedConvertedAmount,
        fee = expectedFee
    )

    @Test
    fun test_convert() {
        val mockCommissionFeeManager = mockk<CommissionFeeManager>()
        every { mockCommissionFeeManager.calculateFee(testAmount, testFromRate) } returns expectedFee

        val manager = ConversionManager(mockCommissionFeeManager)

        val actualResult = manager.convert(testAmount, testFromRate, testToRate)
        assertEquals(expectedConversionResult.from, actualResult.from, 0.0)
        assertEquals(expectedConversionResult.to, actualResult.to, 0.0)
        assertEquals(expectedConversionResult.fee, actualResult.fee, 0.0)
    }
}
