package com.example.currencyexchanger.ui.view.balance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.currencyexchanger.R
import com.example.currencyexchanger.data.model.Balance
import com.example.currencyexchanger.ui.theme.Typography
import com.example.currencyexchanger.util.shimmerPlaceholder

@Composable
fun BalancesOverview(balances: List<Balance>) {
    Column {
        Text(
            text = stringResource(R.string.balances_overview_title),
            style = Typography.subtitle1
        )
        LazyRow(
            modifier = Modifier.padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (balances.isNotEmpty()) {
                items(items = balances, key = { it.currency }) {
                    Text(text = "${it.amount} ${it.currency}")
                }
            } else {
                items(3) {
                    Text(
                        modifier = Modifier.shimmerPlaceholder(),
                        text = "1000 USD"
                    )
                }
            }
        }
    }
}
