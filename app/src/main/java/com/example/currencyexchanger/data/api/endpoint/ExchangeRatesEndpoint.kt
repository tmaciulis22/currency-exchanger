package com.example.currencyexchanger.data.api.endpoint

import com.example.currencyexchanger.data.api.entity.ExchangeRateEntity
import com.example.currencyexchanger.data.model.Currency
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesEndpoint {

    @GET("latest")
    suspend fun getLatestExchangeRates(
        @Query("base") base: Currency,
        @Query("symbols") symbols: List<Currency>
    ): Result<ExchangeRateEntity>
}
