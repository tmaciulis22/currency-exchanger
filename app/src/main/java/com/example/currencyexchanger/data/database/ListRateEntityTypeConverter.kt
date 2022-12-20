package com.example.currencyexchanger.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.currencyexchanger.data.database.entity.RateEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@ProvidedTypeConverter
class ListRateEntityTypeConverter(moshi: Moshi) {

    private val rateEntitiesType = Types.newParameterizedType(List::class.java, RateEntity::class.java)
    private val rateEntitiesAdapter = moshi.adapter<List<RateEntity>>(rateEntitiesType)

    @TypeConverter
    fun stringToRateEntities(string: String): List<RateEntity> {
        return rateEntitiesAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun rateEntitiesToString(rates: List<RateEntity>): String {
        return rateEntitiesAdapter.toJson(rates)
    }
}
