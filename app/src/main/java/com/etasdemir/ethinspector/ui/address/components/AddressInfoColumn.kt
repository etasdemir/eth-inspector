package com.etasdemir.ethinspector.ui.address.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.AccountInfo
import com.etasdemir.ethinspector.ui.components.CardColumnItem
import com.etasdemir.ethinspector.ui.components.CardRowItem
import com.etasdemir.ethinspector.utils.EthUnit
import com.etasdemir.ethinspector.utils.fromWei
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun AddressInfoColumn(address: String, state: AccountInfo) {
    val balanceEth = remember {
        state.balanceWei.fromWei(EthUnit.ETHER).setScale(3, RoundingMode.UP).toPlainString()
    }
    val balanceEthStr =
        stringResource(id = R.string.eth_with_amount, balanceEth)
    val balanceUsdStr =
        stringResource(
            id = R.string.usd_with_amount, BigDecimal(state.balanceUsd)
                .setScale(5, RoundingMode.UP)
                .toPlainString()
        )

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