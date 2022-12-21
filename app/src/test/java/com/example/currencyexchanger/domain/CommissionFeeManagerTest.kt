package com.example.currencyexchanger.domain

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class CommissionFeeManagerTest {

    private val amountToTest = 500.0
    private val exchangeRateEurToTest = 2.45

    @Test
    fun test_calculateFee_zeroFee() {
        val expectedFee = 0.0

        val mockPrefs = mockk<SharedPreferences>()
        every {
            mockPrefs.getLong(CommissionFeeManager.TOTAL_CONVERSIONS, 0)
        } returns 0
        every {
            mockPrefs.getLong(CommissionFeeManager.LAST_CONVERSION, any())
        } returns System.currentTimeMillis()
        every {
            mockPrefs.getLong(CommissionFeeManager.CONVERSIONS_TODAY, 0)
        } returns 0
        val manager = CommissionFeeManager(mockPrefs)

        val actualFee = manager.calculateFee(amountToTest, exchangeRateEurToTest)
        assertEquals(actualFee, expectedFee, 0.0)
    }

    @Test
    fun test_calculateFee_noFreeConversions() {
        val expectedFee = amountToTest * CommissionFeeManager.PERCENTAGE

        val mockPrefs = mockk<SharedPreferences>()
        every {
            mockPrefs.getLong(CommissionFeeManager.TOTAL_CONVERSIONS, 0)
        } returns 6
        every {
            mockPrefs.getLong(CommissionFeeManager.LAST_CONVERSION, any())
        } returns System.currentTimeMillis()
        every {
            mockPrefs.getLong(CommissionFeeManager.CONVERSIONS_TODAY, 0)
        } returns 0
        val manager = CommissionFeeManager(mockPrefs)

        val actualFee = manager.calculateFee(amountToTest, exchangeRateEurToTest)
        assertEquals(actualFee, expectedFee, 0.0)
    }

    @Test
    fun test_calculateFee_exceededDailyConversionsThreshold() {
        val expectedFee =
            amountToTest * CommissionFeeManager.PERCENTAGE_AFTER_THRESHOLD + CommissionFeeManager.EUR_EQUIVALENT * exchangeRateEurToTest

        val mockPrefs = mockk<SharedPreferences>()
        every {
            mockPrefs.getLong(CommissionFeeManager.TOTAL_CONVERSIONS, 0)
        } returns 16
        every {
            mockPrefs.getLong(CommissionFeeManager.LAST_CONVERSION, any())
        } returns System.currentTimeMillis()
        every {
            mockPrefs.getLong(CommissionFeeManager.CONVERSIONS_TODAY, 0)
        } returns 16
        val manager = CommissionFeeManager(mockPrefs)

        val actualFee = manager.calculateFee(amountToTest, exchangeRateEurToTest)
        assertEquals(actualFee, expectedFee, 0.0)
    }
}
