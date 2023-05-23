package com.etasdemir.ethinspector.ui.contract.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.*
import com.etasdemir.ethinspector.utils.format
import timber.log.Timber

data class ContractInfoColumnState(
    val address: String,
    val creatorAddress: String,
    val creationTime: String,
    val txCount: Double,
    val balanceEth: String,
    val balanceUsd: String
)

@Composable
fun ContractInfoColumn(state: ContractInfoColumnState) {
    val onCreatorAddressClick = remember {
        {
            Timber.e("navigate to address detail with ${state.address}")
        }
    }
    val balanceEthStr =
        stringResource(id = R.string.eth_with_amount, state.balanceEth.format(digits = 5))
    val balanceUsdStr =
        stringResource(id = R.string.eth_with_amount, state.balanceUsd.format(digits = 5))

    Column(modifier = Modifier.fillMaxWidth()) {
        CardColumnItem(
            title = stringResource(id = R.string.address),
            body = state.address
        )
        CardColumnItem(
            title = stringResource(id = R.string.creator_address),
            bodyContent = {
                UnderlinedButton(text = state.creatorAddress, onClick = { onCreatorAddressClick() })
                Spacer(modifier = Modifier.height(10.dp))
            }
        )
        CardRowItem(field = stringResource(id = R.string.creation_time), value = state.creationTime)
        CardRowItem(
            field = stringResource(id = R.string.tx_count),
            value = state.txCount.toString()
        )
        CardRowItem(
            field = stringResource(id = R.string.balance),
            value = "$balanceEthStr (${balanceUsdStr})"
        )
    }
}

@Composable
@Preview
fun ContractInfoColumnPreview() {
    val address = "0x123123KHGADSD87SD6ZXC3AH1SDA2SD87AIDS68"
    val creatorAddress = "0x123123KHGADSD87SD6ZXC3AH1SDA2SD87AIDS68"
    val creationTime = "2021-08-30 14:01:29"
    val txCount = 67321.0
    val balanceEth = "29.1273293897"
    val balanceUsd = "35123.1238123"
    val state = ContractInfoColumnState(
        address, creatorAddress, creationTime, txCount, balanceEth, balanceUsd
    )
    ContractInfoColumn(state)
}