package com.example.currencyexchanger.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.currencyexchanger.data.api.endpoint.ExchangeRatesEndpoint
import com.example.currencyexchanger.data.database.MainDatabase
import com.example.currencyexchanger.data.database.dao.BalanceEntityDAO
import com.example.currencyexchanger.data.database.dao.ExchangeRateEntityDAO
import com.example.currencyexchanger.data.repository.BalanceRepository
import com.example.currencyexchanger.data.repository.ExchangeRatesRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "currency-exchanger-prefs", // TODO move it to environment file
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideMainDatabase(@ApplicationContext appContext: Context): MainDatabase {
        return Room.databaseBuilder(
            appContext,
            MainDatabase::class.java,
            "exchanger-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBalanceEntityDAO(mainDatabase: MainDatabase): BalanceEntityDAO {
        return mainDatabase.balanceEntityDAO()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request
                    .newBuilder()
                    .header(
                        "apikey",
                        "i1PMfVUbzMxWJlyZ8ONvqnjbdMZnbKYF" // TODO move it to environment file
                    )
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.apilayer.com/exchangerates_data/") // TODO move it to environment file
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideExchangeRatesEndpoint(retrofit: Retrofit): ExchangeRatesEndpoint {
        return retrofit.create(ExchangeRatesEndpoint::class.java)
    }

    @Provides
    @Singleton
    fun provideBalanceRepository(balanceEntityDAO: BalanceEntityDAO): BalanceRepository {
        return BalanceRepository(balanceEntityDAO)
    }

    @Provides
    @Singleton
    fun provideExchangeRateRepository(
        exchangeRatesEndpoint: ExchangeRatesEndpoint,
        exchangeRateEntityDAO: ExchangeRateEntityDAO
    ): ExchangeRatesRepository {
        return ExchangeRatesRepository(
            exchangeRatesEndpoint,
            exchangeRateEntityDAO
        )
    }
}
