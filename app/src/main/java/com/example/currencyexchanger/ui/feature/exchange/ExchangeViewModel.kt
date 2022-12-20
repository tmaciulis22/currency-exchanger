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
    private val exchangerInputValues = MutableStateFlow<Map<CurrencyInputType, String>>(mapOf())
    private val selectedCurrencies = MutableStateFlow<Map<CurrencyInputType, String>>(mapOf())
    private val showSuccessDialog = MutableStateFlow(false)

    val state = combine(
        balancesManager.balances,
        exchangeRatesSyncManager.exchangeRates,
        conversionResultState,
        exchangerInputValues,
        selectedCurrencies,
//        showSuccessDialog
    ) { balances, exchangeRates, result, inputValues, currencies ->
        ExchangeState(
            balances = balances,
            rates = exchangeRates.rates,
            conversionResult = result,
            exchangerInputValues = inputValues,
            selectedCurrencies = currencies,
//            showSuccessDialog = show
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
                showSuccessDialog.value = true
            }
        }
    }

    fun onExchangeInputChange(newValue: String) { // TODO viewModelScope.launch { }?
        val oldMap = exchangerInputValues.value.toMutableMap()
        oldMap[CurrencyInputType.Sell] = newValue
        val fromCurrency = selectedCurrencies.value[CurrencyInputType.Sell] ?: return
        val toCurrency = selectedCurrencies.value[CurrencyInputType.Receive] ?: return

        convert(
            amount = newValue.toDouble(),
            fromCurrency = fromCurrency,
            toCurrency = toCurrency
        )

        oldMap[CurrencyInputType.Receive] = conversionResultState.value?.to.toString()
        exchangerInputValues.value = oldMap
    }

    fun onSelectedCurrency(type: CurrencyInputType, newCurrency: String) {
        val oldMap = selectedCurrencies.value.toMutableMap()
        oldMap[type] = newCurrency
        selectedCurrencies.value = oldMap

        exchangerInputValues.value[CurrencyInputType.Sell]?.let {
            onExchangeInputChange(it)
        }
    }

    fun onSuccessDialogClose() {
        showSuccessDialog.value = false
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
