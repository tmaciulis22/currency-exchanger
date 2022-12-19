package com.example.currencyexchanger.ui.view.exchange

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.currencyexchanger.R
import com.example.currencyexchanger.data.model.Currency

@Composable
fun CurrencySelector(
    currencies: List<Currency>,
    onSelectedCurrency: (Currency) -> Unit,
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedCurrency by remember {
        mutableStateOf(currencies.firstOrNull() ?: Currency.EUR)
    }

    Box {
        TextButton(
            onClick = { expanded = true },
            colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = selectedCurrency.name)
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.currency_selector_dropdown_icon_description)
                )
            }
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            currencies.forEach {
                DropdownMenuItem(onClick = {
                    onSelectedCurrency(it)
                    selectedCurrency = it
                    expanded = false
                }) {
                    Text(text = it.name)
                }
            }
        }
    }
}
