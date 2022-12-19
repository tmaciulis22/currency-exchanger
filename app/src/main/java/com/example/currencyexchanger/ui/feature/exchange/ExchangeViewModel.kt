package com.example.currencyexchanger.ui.feature.exchange

import androidx.lifecycle.ViewModel
import com.example.currencyexchanger.data.repository.BalanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val balanceRepository: BalanceRepository
) : ViewModel() {

}
