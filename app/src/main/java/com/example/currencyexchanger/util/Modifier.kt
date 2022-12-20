package com.example.currencyexchanger.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

fun Modifier.shimmerPlaceholder(visible: Boolean = true): Modifier = composed {
    Modifier.placeholder(visible = visible, highlight = PlaceholderHighlight.shimmer())
}
