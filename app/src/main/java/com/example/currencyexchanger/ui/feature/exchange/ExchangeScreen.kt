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
    val state = exchangeViewModel.state.collectAsState()
    val showSuccessDialog = exchangeViewModel.showSuccessDialog.collectAsState()

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        BalancesOverview(balances = state.value.balances)
        CurrencyExchanger(
            inputValues = state.value.exchangerInputValues,
            onInputChange = {
                exchangeViewModel.onExchangeInputChange(it)
            },
            fromCurrencies = state.value.balances.map { it.currency },
            toCurrencies = state.value.rates.keys.toList(),
            onSelectedCurrency = { type, newCurrency ->
                exchangeViewModel.onSelectedCurrency(type, newCurrency)
            },
            selectedCurrencies = state.value.selectedCurrencies
        )
        Spacer(modifier = Modifier.weight(1f))
        RoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(12.dp),
            onClick = {
                exchangeViewModel.submitConversion()
            },
            text = stringResource(id = R.string.exchange_screen_action_button_text)
        )
        InfoDialog(
            show = showSuccessDialog.value,
            onClose = { exchangeViewModel.onSuccessDialogClose() },
            title = stringResource(id = R.string.successful_conversion_title),
            description = stringResource(
                id = R.string.successful_conversion_description,
                state.value.fromAmount.toString(),
                state.value.fromCurrency.toString(),
                state.value.toAmount.toString(),
                state.value.toCurrency.toString(),
                state.value.commissionFee.toString(),
                state.value.fromCurrency.toString()
            )
        )
    }
}
