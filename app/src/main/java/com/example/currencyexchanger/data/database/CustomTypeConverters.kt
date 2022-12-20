package com.example.currencyexchanger.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@ProvidedTypeConverter
class CustomTypeConverters(val moshi: Moshi) {

    @TypeConverter
    fun fromStringToStringDoubleMap(value: String): Map<String, Double>? {
        return stringToMap(value)
    }

    @TypeConverter
    fun fromStringDoubleMapToString(value: Map<String, Double>): String {
        return mapToString(value)
    }

    inline fun <reified K, reified V> stringToMap(string: String): Map<K, V>? =
        getMoshiMapAdapter<K, V>().fromJson(string)

    inline fun <reified K, reified V> mapToString(map: Map<K, V>?): String =
        getMoshiMapAdapter<K, V>().toJson(map)

    inline fun <reified K, reified V> getMoshiMapAdapter(): JsonAdapter<Map<K, V>> {
        return Types.newParameterizedType(Map::class.java, K::class.java, V::class.java).let {
            moshi.adapter(it)
        }
    }
}
