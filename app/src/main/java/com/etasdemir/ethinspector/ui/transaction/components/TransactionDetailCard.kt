package com.etasdemir.ethinspector.ui.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.*
import com.etasdemir.ethinspector.ui.transaction.TransactionDetailState
import com.etasdemir.ethinspector.utils.format
import com.etasdemir.ethinspector.utils.toPlainString
import timber.log.Timber

@Composable
fun TransactionDetailCard(state: TransactionDetailState) {
    val onAddressClick = remember {
        { address: String ->
            Timber.e("navigate to address screen $address")
        }
    }

    val gasPriceStr = remember {
        state.gasPrice.toPlainString().format(6)
    }
    val maxFeePerGasStr = remember {
        state.maxFeePerGas?.toPlainString()?.format(6)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .background(MaterialTheme.colorScheme.background)
            .padding(25.dp)
    )
    {
        CardColumnItem(
            title = stringResource(id = R.string.from),
            bodyContent = {
                UnderlinedButton(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = state.fromAddress,
                    onClick = { onAddressClick(state.fromAddress) })
            }
        )
        CardColumnItem(
            title = stringResource(id = R.string.to),
            bodyContent = {
                UnderlinedButton(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = state.toAddress,
                    onClick = { onAddressClick(state.toAddress!!) })
            }
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.gas_amount)) },
            value = state.gasAmount.toString()
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.gas_price)) },
            value = stringResource(id = R.string.gwei_with_amount, gasPriceStr)
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.max_fee_per_gas)) },
            value = if (maxFeePerGasStr != null) stringResource(
                id = R.string.gwei_with_amount,
                maxFeePerGasStr
            ) else null
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.nonce)) },
            value = state.nonce
        )
    }
}


@Composable
@Preview
fun TransactionDetailCardPreview() {
    val state = TransactionDetailState(
        // Info Card
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        "21.02.2020 13:57:45",
        5301614u,
        0.0123151,
        0.00012531,

        // Detail Card
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        21999.0,
        14.0,
        16.0,
        2,
        "40351"
    )
    TransactionDetailCard(state)
}