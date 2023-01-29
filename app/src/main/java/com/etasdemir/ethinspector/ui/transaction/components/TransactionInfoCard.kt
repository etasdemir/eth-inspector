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
import com.etasdemir.ethinspector.utils.format
import timber.log.Timber

data class TransactionInfoCardState(
    val transactionHash: String,
    val time: String,
    val block: String,
    val amount: Double,
    val fee: String
)

@Composable
fun TransactionInfoCard(state: TransactionInfoCardState) {
    val onBlockClick = remember {
        { block: String ->
            Timber.e("Navigate to block: $block")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .background(MaterialTheme.colorScheme.background)
            .padding(25.dp)
    ) {
        CardColumnItem(
            title = stringResource(id = R.string.transaction_hash),
            body = state.transactionHash
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.time)) },
            value = state.time
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.block)) }
        ) {
            UnderlinedButton(
                text = state.block, onClick = onBlockClick
            )
        }
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.amount)) },
            value = stringResource(id = R.string.eth_with_amount, state.amount.format(5))
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.fee)) },
            value = state.fee
        )
    }
}


@Composable
@Preview
fun TransactionInfoCardPreview() {
    val state = TransactionInfoCardState(
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        "21.02.2020 13:57:45",
        "5301614",
        0.0123151,
        "0.00012531"
    )
    TransactionInfoCard(state)
}