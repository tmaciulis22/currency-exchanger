package com.example.currencyexchanger.ui.feature.exchange

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyexchanger.R
import com.example.currencyexchanger.ui.view.balance.BalancesOverview
import com.example.currencyexchanger.ui.view.core.InfoDialog
import com.example.currencyexchanger.ui.view.core.RoundedButton
import com.example.currencyexchanger.ui.view.exchange.CurrencyExchanger

@Composable
fun ExchangeScreen(
    exchangeViewModel: ExchangeViewModel = hiltViewModel()
) {
    val dataState = exchangeViewModel.dataState.collectAsState()
    val uiState = exchangeViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        BalancesOverview(balances = dataState.value.balances)
        CurrencyExchanger(
            inputValues = uiState.value.exchangerInputValues,
            onInputChange = {
                exchangeViewModel.onExchangeInputChange(it)
            },
            fromCurrencies = dataState.value.fromCurrencies,
            toCurrencies = dataState.value.toCurrencies,
            onSelectedCurrency = { type, newCurrency ->
                exchangeViewModel.onSelectedCurrency(type, newCurrency)
            },
            selectedCurrencies = uiState.value.selectedCurrencies
        )
        Spacer(modifier = Modifier.weight(1f))
        RoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(12.dp),
            onClick = {
                exchangeViewModel.onSubmit()
            },
            text = stringResource(id = R.string.exchange_screen_action_button_text),
        )
        InfoDialog(
            show = uiState.value.showSuccessDialog,
            onClose = { exchangeViewModel.onSuccessDialogClose() },
            title = stringResource(id = R.string.successful_conversion_title),
            description = stringResource(
                id = R.string.successful_conversion_description,
                dataState.value.fromAmount.toString(),
                uiState.value.fromCurrency.toString(),
                dataState.value.toAmount.toString(),
                uiState.value.toCurrency.toString(),
                dataState.value.commissionFee.toString(),
                uiState.value.fromCurrency.toString()
            )
        )
    }
}
