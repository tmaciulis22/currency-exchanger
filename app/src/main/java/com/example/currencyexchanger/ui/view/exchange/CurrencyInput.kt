package com.example.currencyexchanger.ui.view.exchange

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.currencyexchanger.R
import com.example.currencyexchanger.data.model.Currency

@Composable
fun CurrencyInput(
    modifier: Modifier = Modifier,
    type: CurrencyInputType,
    inputValue: String,
    onInputChange: (String) -> Unit,
    onSelectedCurrency: (Currency) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = inputValue,
            onValueChange = onInputChange,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = type.icon,
                    contentDescription = stringResource(type.contentDescription),
                    tint = type.tint
                )
            },
            label = {
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = type.name
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
            )
        )
        CurrencySelector(currencies = Currency.values().toList(), onSelectedCurrency = onSelectedCurrency)
    }
}

enum class CurrencyInputType(
    val icon: ImageVector,
    @StringRes val contentDescription: Int,
    val tint: Color,
) {
    Sell(
        Icons.Filled.KeyboardArrowUp,
        R.string.currency_input_sell_icon_description,
        Color.Red
    ),
    Receive(
        Icons.Filled.KeyboardArrowDown,
        R.string.currency_input_receive_icon_description,
        Color.Green
    );
}
