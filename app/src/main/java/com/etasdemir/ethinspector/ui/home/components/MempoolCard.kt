package com.etasdemir.ethinspector.ui.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.CardRowItem

@Composable
@Preview
fun MempoolCard() {
    // TODO delete later.
    val transactions = "5163"
    val tps = "5"
    val pendingTxValue = "79,21"

    RoundedTitleCard(title = stringResource(id = R.string.mempool)) {
        CardRowItem(field = stringResource(id = R.string.transactions), value = transactions)
        CardRowItem(field = stringResource(id = R.string.transaction_per_sec), value = tps)
        CardRowItem(
            field = stringResource(id = R.string.values_of_pending_tx),
            value = stringResource(id = R.string.eth_with_amount, pendingTxValue)
        )
    }
}