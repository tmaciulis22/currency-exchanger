package com.example.currencyexchanger.domain

import android.content.SharedPreferences
import javax.inject.Inject

class CommissionFeeManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val numOfFreeConversions: Int = NUM_OF_FREE_CONVERSIONS,
    private val percentage: Double = PERCENTAGE,
    private val conversionsPerDayThreshold: Int = CONVERSIONS_PER_DAY_THRESHOLD,
    private val percentageAfterThreshold: Double = PERCENTAGE_AFTER_THRESHOLD,
    private val eurEquivalent: Double = EUR_EQUIVALENT
) {

    fun calculateFee(
        amount: Double,
        exchangeRateEur: Double
    ): Double {
        val totalConversions = sharedPreferences.getLong(TOTAL_CONVERSIONS, 0)
        val lastConversion = sharedPreferences.getLong(LAST_CONVERSION, System.currentTimeMillis())
        val conversionsToday = if (System.currentTimeMillis() - lastConversion >= DAY_IN_MILLIS)
            0
        else
            sharedPreferences.getLong(CONVERSIONS_TODAY, 0)

        val commissionFee = when {
            totalConversions < numOfFreeConversions -> 0.0
            conversionsToday <= conversionsPerDayThreshold -> amount * percentage
            else -> amount * percentageAfterThreshold + eurEquivalent * exchangeRateEur
        }

        return commissionFee
    }

    fun updateConversionsCount() {
        val totalConversions = sharedPreferences.getLong(TOTAL_CONVERSIONS, 0)
        val lastConversion = sharedPreferences.getLong(LAST_CONVERSION, System.currentTimeMillis())
        val conversionsToday = if (System.currentTimeMillis() - lastConversion >= DAY_IN_MILLIS)
            0
        else
            sharedPreferences.getLong(CONVERSIONS_TODAY, 0)

        sharedPreferences.apply {
            edit().putLong(TOTAL_CONVERSIONS, totalConversions + 1).apply()
            edit().putLong(LAST_CONVERSION, System.currentTimeMillis()).apply()
            edit().putLong(CONVERSIONS_TODAY, conversionsToday + 1).apply()
        }
    }

    companion object {
        private const val NUM_OF_FREE_CONVERSIONS = 5
        private const val PERCENTAGE = 0.007
        private const val CONVERSIONS_PER_DAY_THRESHOLD = 15
        private const val PERCENTAGE_AFTER_THRESHOLD = 0.012
        private const val EUR_EQUIVALENT = 0.3

        private const val TOTAL_CONVERSIONS = "TOTAL_CONVERSIONS"
        private const val CONVERSIONS_TODAY = "CONVERSIONS_TODAY"
        private const val LAST_CONVERSION = "LAST_CONVERSION"

        private const val DAY_IN_MILLIS = 86400000L
    }
}
