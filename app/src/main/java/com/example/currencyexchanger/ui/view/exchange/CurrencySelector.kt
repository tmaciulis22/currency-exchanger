package com.example.currencyexchanger.ui.view.exchange

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.currencyexchanger.R
import com.example.currencyexchanger.util.shimmerPlaceholder

@Composable
fun CurrencySelector(
    currencies: List<String>,
    selectedCurrency: String,
    onSelectedCurrency: (String) -> Unit,
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Box {
        TextButton(
            modifier = Modifier.shimmerPlaceholder(visible = currencies.isEmpty()),
            onClick = { expanded = true },
            colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = selectedCurrency,
                    color = MaterialTheme.colors.onBackground
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.currency_selector_dropdown_icon_description),
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencies.forEach {
                DropdownMenuItem(onClick = {
                    onSelectedCurrency(it)
                    expanded = false
                }) {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}
