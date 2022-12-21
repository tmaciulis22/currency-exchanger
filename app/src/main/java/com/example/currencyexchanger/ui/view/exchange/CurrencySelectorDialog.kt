package com.example.currencyexchanger.ui.view.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.example.currencyexchanger.R

@Composable
fun CurrencySelectorDialog(
    currencies: List<String>,
    selectedCurrency: String,
    onDismissRequest: () -> Unit,
    onSelectedCurrency: (String) -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
        ) {
            items(currencies, { it }) {
                Row {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onSelectedCurrency(it)
                            onDismissRequest()
                        }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = it)
                            if (selectedCurrency == it)
                                Icon(Icons.Filled.Check, stringResource(id = R.string.currency_picker_check_icon_description))
                        }
                    }
                }
            }
        }
    }
}
