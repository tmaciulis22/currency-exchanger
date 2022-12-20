package com.example.currencyexchanger.ui.feature.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchanger.data.model.ConversionResult
import com.example.currencyexchanger.domain.BalancesManager
import com.example.currencyexchanger.domain.ConversionManager
import com.example.currencyexchanger.domain.ExchangeRatesSyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeRatesSyncManager: ExchangeRatesSyncManager,
    private val conversionManager: ConversionManager,
    private val balancesManager: BalancesManager
) : ViewModel() {

    private val conversionResultState = MutableStateFlow<ConversionResult?>(null)

    val state = combine(
        balancesManager.balances,
        conversionResultState
    ) { balances, result ->
        ExchangeState(balances, result)
    }.stateIn(
        scope = viewModelScope + Dispatchers.IO,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ExchangeState()
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            exchangeRatesSyncManager.start()
        }
    }

    fun convert(
        amount: Double,
        fromCurrency: String,
        toCurrency: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val conversionResult = conversionManager.convert(
                amount,
                fromCurrency,
                toCurrency
            )
            val updateBalancesResult = balancesManager.updateBalances(
                conversionResult,
                fromCurrency,
                toCurrency
            )

            if (updateBalancesResult.isSuccess)
                conversionResultState.value = conversionResult
        }
    }
}
