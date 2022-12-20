package com.example.currencyexchanger.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.currencyexchanger.data.api.endpoint.ExchangeRatesEndpoint
import com.example.currencyexchanger.data.database.CustomTypeConverters
import com.example.currencyexchanger.data.database.MainDatabase
import com.example.currencyexchanger.data.database.dao.BalanceEntityDAO
import com.example.currencyexchanger.data.database.dao.ExchangeRatesEntityDAO
import com.example.currencyexchanger.data.repository.BalanceRepository
import com.example.currencyexchanger.data.repository.ExchangeRatesRepository
import com.example.currencyexchanger.domain.BalancesManager
import com.example.currencyexchanger.domain.CommissionFeeManager
import com.example.currencyexchanger.domain.ConversionManager
import com.example.currencyexchanger.domain.ExchangeRatesSyncManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule { // TODO split it to separate modules

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "currency-exchanger-prefs", // TODO move it to environment file
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideMainDatabase(@ApplicationContext appContext: Context, moshi: Moshi): MainDatabase {
        return Room.databaseBuilder(
            appContext,
            MainDatabase::class.java,
            "exchanger-database"
        )
            .addTypeConverter(CustomTypeConverters(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideBalanceEntityDAO(mainDatabase: MainDatabase): BalanceEntityDAO {
        return mainDatabase.balanceEntityDAO()
    }

    @Provides
    @Singleton
    fun provideExchangeRatesEntityDAO(mainDatabase: MainDatabase): ExchangeRatesEntityDAO {
        return mainDatabase.exchangeRatesEntityDAO()
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
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
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
        exchangeRatesEntityDAO: ExchangeRatesEntityDAO
    ): ExchangeRatesRepository {
        return ExchangeRatesRepository(
            exchangeRatesEndpoint,
            exchangeRatesEntityDAO
        )
    }

    @Provides
    @Singleton
    fun provideExchangeRatesSyncManager(
        exchangeRatesRepository: ExchangeRatesRepository
    ): ExchangeRatesSyncManager {
        return ExchangeRatesSyncManager(exchangeRatesRepository)
    }

    @Provides
    @Singleton
    fun provideCommissionFeeManager(
        sharedPreferences: SharedPreferences
    ): CommissionFeeManager {
        return CommissionFeeManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideConversionManager(
        commissionFeeManager: CommissionFeeManager,
        exchangeRatesRepository: ExchangeRatesRepository
    ): ConversionManager {
        return ConversionManager(
            commissionFeeManager,
            exchangeRatesRepository
        )
    }

    @Provides
    @Singleton
    fun provideBalancesManager(
        balanceRepository: BalanceRepository
    ): BalancesManager {
        return BalancesManager(balanceRepository)
    }
}
