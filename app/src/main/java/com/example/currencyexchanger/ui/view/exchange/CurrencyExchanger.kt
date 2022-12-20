package com.example.currencyexchanger.ui.view.exchange

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.currencyexchanger.R
import com.example.currencyexchanger.ui.theme.Typography

@Composable
fun CurrencyExchanger(
    inputValues: Map<CurrencyInputType, String>,
    onInputChange: (String) -> Unit,
    fromCurrencies: List<String>,
    toCurrencies: List<String>,
    selectedCurrencies: Map<CurrencyInputType, String>,
    onSelectedCurrency: (CurrencyInputType, String) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = 18.dp),
            text = stringResource(R.string.currency_exchanger_title),
            style = Typography.subtitle1
        )
        CurrencyInput(
            modifier = Modifier.padding(bottom = 18.dp),
            type = CurrencyInputType.Sell,
            inputValue = inputValues[CurrencyInputType.Sell] ?: "0.00",
            onInputChange = onInputChange,
            onSelectedCurrency = {
                onSelectedCurrency(CurrencyInputType.Sell, it)
            },
            currencies = fromCurrencies,
            selectedCurrency = selectedCurrencies[CurrencyInputType.Sell] ?: "EUR"
        )
        CurrencyInput(
            modifier = Modifier.padding(bottom = 18.dp),
            type = CurrencyInputType.Receive,
            inputValue = inputValues[CurrencyInputType.Receive] ?: "0.00",
            onSelectedCurrency = {
                onSelectedCurrency(CurrencyInputType.Receive, it)
            },
            currencies = toCurrencies,
            selectedCurrency = selectedCurrencies[CurrencyInputType.Receive] ?: "USD"
        )
    }
}
