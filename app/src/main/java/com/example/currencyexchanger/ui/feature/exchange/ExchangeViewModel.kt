package com.example.currencyexchanger.ui.feature.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchanger.data.model.Balance
import com.example.currencyexchanger.data.model.ConversionResult
import com.example.currencyexchanger.data.model.ExchangeRates
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
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeRatesSyncManager: ExchangeRatesSyncManager,
    private val conversionManager: ConversionManager,
    private val balancesManager: BalancesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExchangeUIState())
    val uiState = _uiState.asStateFlow()

    val dataState = combine(
        balancesManager.balances,
        exchangeRatesSyncManager.exchangeRates,
        conversionManager.conversionResult,
    ) { balances, exchangeRates, result ->
        if (_uiState.value.selectedCurrencies.isEmpty())
            initSelectedCurrencies(balances, exchangeRates)
        result?.let { updateReceiveValue(it) }

        ExchangeDataState(
            balances = balances,
            rates = exchangeRates.rates,
            conversionResult = result,
        )
    }.stateIn(
        scope = viewModelScope + Dispatchers.IO,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ExchangeDataState()
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            exchangeRatesSyncManager.start()
        }
    }

    fun onSubmit() {
        viewModelScope.launch(Dispatchers.IO) {
            val fromAmount = _uiState.value.sellAmount?.toDoubleOrNull() ?: return@launch
            val fromCurrency = _uiState.value.fromCurrency ?: return@launch
            val toCurrency = _uiState.value.toCurrency ?: return@launch

            if (fromCurrency == toCurrency || fromAmount <= 0.0) return@launch

            val conversionResult = conversionManager.convert(
                fromAmount,
                dataState.value.rates[fromCurrency] ?: 1.0,
                dataState.value.rates[toCurrency] ?: 1.0
            )
            val updateBalancesResult = balancesManager.updateBalances(
                conversionResult,
                fromCurrency,
                toCurrency
            )

            if (updateBalancesResult.isSuccess) {
                conversionManager.updateConversionsCount()
                _uiState.update {
                    it.copy(showSuccessDialog = true)
                }
            }
        }
    }

    fun onExchangeInputChange(newValue: String) {
        _uiState.update {
            val newMap = it.exchangerInputValues.toMutableMap()
            newMap[CurrencyInputType.Sell] = newValue
            val fromCurrency = it.fromCurrency ?: return
            val toCurrency = it.toCurrency ?: return

            conversionManager.convert(
                newValue.toDouble(),
                dataState.value.rates[fromCurrency] ?: 1.0,
                dataState.value.rates[toCurrency] ?: 1.0
            )

            it.copy(exchangerInputValues = newMap)
        }
    }

    fun onSelectedCurrency(type: CurrencyInputType, newCurrency: String) {
        _uiState.update {
            val newMap = it.selectedCurrencies.toMutableMap()
            newMap[type] = newCurrency
            it.copy(selectedCurrencies = newMap)
        }

        _uiState.value.sellAmount?.let {
            if (it.toDouble() > 0.0) {
                onExchangeInputChange(it)
            }
        }
    }

    fun onSuccessDialogClose() {
        _uiState.update {
            it.copy(showSuccessDialog = false)
        }
    }

    private fun initSelectedCurrencies(balances: List<Balance>, exchangeRates: ExchangeRates) {
        _uiState.update {
            it.copy(
                selectedCurrencies = mapOf(
                    CurrencyInputType.Sell to (balances.firstOrNull()?.currency ?: "EUR"),
                    CurrencyInputType.Receive to (exchangeRates.rates.keys.firstOrNull() ?: "USD")
                )
            )
        }
    }

    private fun updateReceiveValue(conversionResult: ConversionResult) {
        _uiState.update {
            val newMap = it.exchangerInputValues.toMutableMap()
            newMap[CurrencyInputType.Receive] = conversionResult.to.toString()
            it.copy(exchangerInputValues = newMap)
        }
    }
}
