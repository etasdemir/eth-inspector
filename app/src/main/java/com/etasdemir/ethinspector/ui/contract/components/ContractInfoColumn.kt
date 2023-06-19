package com.etasdemir.ethinspector.ui.contract.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.ContractInfo
import com.etasdemir.ethinspector.ui.components.*
import com.etasdemir.ethinspector.utils.EthUnit
import com.etasdemir.ethinspector.utils.fromWei
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun ContractInfoColumn(state: ContractInfo, address: String, navigateToAccount: (String) -> Unit) {
    val onCreatorAddressClick = remember {
        {
            navigateToAccount(state.creatorAddress)
        }
    }
    val balanceEth = remember {
        state.balanceWei.fromWei(EthUnit.ETHER).setScale(3, RoundingMode.UP).toPlainString()
    }
    val balanceEthStr =
        stringResource(id = R.string.eth_with_amount, balanceEth)
    val balanceUsdStr =
        stringResource(
            id = R.string.usd_with_amount,
            BigDecimal(state.balanceUsd).setScale(5, RoundingMode.UP).toPlainString()
        )

    Column(modifier = Modifier.fillMaxWidth()) {
        CardColumnItem(
            title = stringResource(id = R.string.address),
            body = address
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
    val txCount: Long = 67321
    val balanceEth = "29.1273293897"
    val balanceUsd = 35123.1238123
    val state = ContractInfo(
        address, creatorAddress, creationTime, txCount, balanceEth, balanceUsd
    )
    ContractInfoColumn(state, address) {}
}