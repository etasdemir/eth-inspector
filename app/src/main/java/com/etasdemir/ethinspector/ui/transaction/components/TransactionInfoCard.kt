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
import com.etasdemir.ethinspector.data.domain_model.Transaction
import com.etasdemir.ethinspector.ui.components.*
import com.etasdemir.ethinspector.utils.format
import com.etasdemir.ethinspector.utils.getDateString
import timber.log.Timber

@Composable
fun TransactionInfoCard(transaction: Transaction) {
    val onBlockClick = remember {
        { block: String ->
            Timber.e("Navigate to block: $block")
        }
    }
    val convertedDate =
        if (transaction.timestamp != null) getDateString(transaction.timestamp.toLong()) else null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .background(MaterialTheme.colorScheme.background)
            .padding(25.dp)
    ) {
        CardColumnItem(
            title = stringResource(id = R.string.transaction_hash),
            body = transaction.transactionHash
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.time)) },
            value = convertedDate
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.block)) }
        ) {
            UnderlinedButton(
                text = transaction.block.toString(), onClick = onBlockClick
            )
        }
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.amount)) },
            value = stringResource(id = R.string.eth_with_amount, transaction.amount.format(5))
        )
        CardRowItem(
            leftContent = { BodyTitleText(text = stringResource(id = R.string.fee)) },
            value = transaction.fee.toString().format(6)
        )
    }
}


@Composable
@Preview
fun TransactionInfoCardPreview() {
    val state = Transaction(
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
    TransactionInfoCard(state)
}