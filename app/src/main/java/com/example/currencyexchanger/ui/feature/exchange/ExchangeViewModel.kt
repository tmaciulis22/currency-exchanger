package com.example.currencyexchanger.ui.feature.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchanger.data.model.ConversionResult
import com.example.currencyexchanger.domain.BalancesManager
import com.example.currencyexchanger.domain.ConversionManager
import com.example.currencyexchanger.domain.ExchangeRatesSyncManager
import com.example.currencyexchanger.ui.view.exchange.CurrencyInputType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeRatesSyncManager: ExchangeRatesSyncManager,
    private val conversionManager: ConversionManager,
    private val balancesManager: BalancesManager
) : ViewModel() {

    private val conversionResultState = MutableStateFlow<ConversionResult?>(null)

    private val exchangerInputValues = MutableStateFlow(
        mapOf(
            CurrencyInputType.Sell to "0.00",
            CurrencyInputType.Receive to "0.00"
        )
    )
    private val selectedCurrencies = MutableStateFlow(
        mapOf(
            CurrencyInputType.Sell to "EUR",
            CurrencyInputType.Receive to "USD"
        )
    )
    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog
        get() = _showSuccessDialog.asStateFlow()

    val state = combine(
        balancesManager.balances,
        exchangeRatesSyncManager.exchangeRates,
        conversionResultState,
        exchangerInputValues,
        selectedCurrencies,
    ) { balances, exchangeRates, result, inputValues, currencies ->
        ExchangeState(
            balances = balances,
            rates = exchangeRates.rates,
            conversionResult = result,
            exchangerInputValues = inputValues,
            selectedCurrencies = currencies
        )
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

    fun submitConversion() {
        viewModelScope.launch(Dispatchers.IO) {
            val conversionResult = conversionResultState.value ?: return@launch
            val fromCurrency = selectedCurrencies.value[CurrencyInputType.Sell] ?: return@launch
            val toCurrency = selectedCurrencies.value[CurrencyInputType.Receive] ?: return@launch

            val updateBalancesResult = balancesManager.updateBalances(
                conversionResult,
                fromCurrency,
                toCurrency
            )

            if (updateBalancesResult.isSuccess) {
                conversionManager.updateConversionsCount()
                withContext(Dispatchers.Main) {
                    _showSuccessDialog.value = true
                }
            }
        }
    }

    fun onExchangeInputChange(newValue: String) {
        exchangerInputValues.update { inputs ->
            val newMap = inputs.toMutableMap()
            newMap[CurrencyInputType.Sell] = newValue
            val fromCurrency = selectedCurrencies.value[CurrencyInputType.Sell] ?: return
            val toCurrency = selectedCurrencies.value[CurrencyInputType.Receive] ?: return

            convert(
                amount = newValue.toDouble(),
                fromCurrency = fromCurrency,
                toCurrency = toCurrency
            )

            newMap[CurrencyInputType.Receive] = conversionResultState.value?.to.toString()

            newMap
        }
    }

    fun onSelectedCurrency(type: CurrencyInputType, newCurrency: String) {
        selectedCurrencies.update {
            val newMap = it.toMutableMap()
            newMap[type] = newCurrency
            newMap
        }

        exchangerInputValues.value[CurrencyInputType.Sell]?.let {
            onExchangeInputChange(it)
        }
    }

    fun onSuccessDialogClose() {
        _showSuccessDialog.value = false
    }

    private fun convert(
        amount: Double,
        fromCurrency: String,
        toCurrency: String
    ) {
        val conversionResult = conversionManager.convert(
            amount,
            state.value.rates[fromCurrency] ?: 1.0,
            state.value.rates[toCurrency] ?: 1.0
        )

        conversionResultState.value = conversionResult
    }
}
