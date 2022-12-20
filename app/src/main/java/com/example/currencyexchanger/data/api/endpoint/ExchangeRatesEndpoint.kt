package com.example.currencyexchanger.data.api.endpoint

import com.example.currencyexchanger.data.api.entity.ExchangeRateEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesEndpoint {

    @GET("latest")
    suspend fun getLatestExchangeRate(
        @Query("base") base: String? = null,
        @Query("symbols") symbols: List<String>? = null
    ): Result<ExchangeRateEntity>
}
