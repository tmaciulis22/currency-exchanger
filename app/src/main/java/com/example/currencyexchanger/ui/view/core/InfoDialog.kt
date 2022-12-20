package com.example.currencyexchanger.ui.view.core

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.currencyexchanger.ui.theme.Typography

@Composable
fun InfoDialog(
    show: Boolean,
    onClose: () -> Unit,
    title: String,
    description: String,
) {
    if (show) {
        AlertDialog(
            onDismissRequest = {
                onClose()
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = description, style = Typography.subtitle1)
            },
            confirmButton = {
                Button(
                    onClick = {
                        onClose()
                    }
                ) {
                    Text("OK")
                }
            },
        )
    }
}
