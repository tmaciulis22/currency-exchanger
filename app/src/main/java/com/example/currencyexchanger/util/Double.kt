package com.example.currencyexchanger.util

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()
