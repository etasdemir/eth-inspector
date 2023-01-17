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
import timber.log.Timber

data class TransactionDetailCardState(
    val fromAddress: String,
    val toAddress: String,
    val gasAmount: String,
    val gasPrice: String,
    val maxFeePerGas: String,
    val txType: String,
    val nonce: Double
)

@Composable
fun TransactionDetailCard(txDetailState: TransactionDetailCardState) {
    val onAddressClick = remember {
        { address: String ->
            Timber.e("navigate to address screen $address")
        }
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
                    text = txDetailState.fromAddress,
                    onClick = { onAddressClick(txDetailState.fromAddress) })
            }
        )
        CardColumnItem(
            title = stringResource(id = R.string.to),
            bodyContent = {
                UnderlinedButton(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = txDetailState.toAddress,
                    onClick = { onAddressClick(txDetailState.toAddress) })
            }
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.gas_amount)) },
            value = txDetailState.gasAmount
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.gas_price)) },
            value = txDetailState.gasPrice
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.max_fee_per_gas)) },
            value = txDetailState.maxFeePerGas
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.nonce)) },
            value = txDetailState.nonce.toString()
        )
    }
}


@Composable
@Preview
fun TransactionDetailCardPreview() {
    val state = TransactionDetailCardState(
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        "21999",
        "14.418428466",
        "16.418428466",
        "2 (EIP-1559)",
        40351.0
    )
    TransactionDetailCard(state)
}