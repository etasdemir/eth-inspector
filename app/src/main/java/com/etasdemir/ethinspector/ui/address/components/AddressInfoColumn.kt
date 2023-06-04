package com.etasdemir.ethinspector.ui.address.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.CardColumnItem
import com.etasdemir.ethinspector.ui.components.CardRowItem
import com.etasdemir.ethinspector.utils.*

data class AddressInfoColumnState(
    val balanceWei: String,
    val balanceUsd: Double,
    val transactionCount: Long
)

@Composable
fun AddressInfoColumn(address: String, state: AddressInfoColumnState) {
    val balanceEth = remember {
        state.balanceWei.fromWei(EthUnit.ETHER).toString().format(3)
    }
    val balanceEthStr =
        stringResource(id = R.string.eth_with_amount, balanceEth)
    val balanceUsdStr =
        stringResource(id = R.string.usd_with_amount, state.balanceUsd.format(digits = 5))

    CardColumnItem(title = stringResource(id = R.string.address), body = address)
    CardColumnItem(
        title = stringResource(id = R.string.balance),
        body = "$balanceEthStr (${balanceUsdStr})"
    )
    CardRowItem(
        field = stringResource(id = R.string.tx_count),
        value = state.transactionCount.toString()
    )
}