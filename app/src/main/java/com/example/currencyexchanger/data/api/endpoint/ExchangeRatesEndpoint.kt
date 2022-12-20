package com.example.currencyexchanger.data.api.endpoint

import com.example.currencyexchanger.data.api.entity.ExchangeRatesEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesEndpoint {

    @GET("latest")
    suspend fun getLatestExchangeRates(
        @Query("base") base: String? = null,
        @Query("symbols") symbols: List<String>? = null
    ): Response<ExchangeRatesEntity>
}
