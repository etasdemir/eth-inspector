package com.etasdemir.ethinspector.ui.address.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.CardColumnItem
import com.etasdemir.ethinspector.ui.components.CardRowItem
import com.etasdemir.ethinspector.utils.format

data class AddressInfoColumnState(
    val address: String,
    val balanceEth: String,
    val balanceUsd: String,
    val txCount: Double
)

@Composable
fun AddressInfoColumn(state: AddressInfoColumnState) {
    val balanceEthStr =
        stringResource(id = R.string.eth_with_amount, state.balanceEth.format(digits = 5))
    val balanceUsdStr =
        stringResource(id = R.string.eth_with_amount, state.balanceUsd.format(digits = 5))

    CardColumnItem(title = stringResource(id = R.string.address), body = state.address)
    CardColumnItem(
        title = stringResource(id = R.string.balance),
        body = "$balanceEthStr (${balanceUsdStr})"
    )
    CardRowItem(field = stringResource(id = R.string.tx_count), value = state.txCount.toString())
}