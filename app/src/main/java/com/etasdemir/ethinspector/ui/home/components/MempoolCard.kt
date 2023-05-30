package com.etasdemir.ethinspector.ui.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.CardRowItem
import com.etasdemir.ethinspector.utils.*

data class MempoolState(
    val transactions: String,
    val tps: String,
    val pendingTxValue: String
)

@Composable
fun MempoolCard(state: MempoolState) {
    val formattedTps = remember { state.tps.format(2) }
    val formattedTxValue = remember {
        state.pendingTxValue.fromWei(EthUnit.ETHER).toString().format(2)
    }

    RoundedTitleCard(title = stringResource(id = R.string.mempool)) {
        CardRowItem(
            field = stringResource(id = R.string.transactions),
            value = state.transactions.addDots()
        )
        CardRowItem(
            field = stringResource(id = R.string.transaction_per_sec),
            value = formattedTps
        )
        CardRowItem(
            field = stringResource(id = R.string.values_of_pending_tx),
            value = stringResource(
                id = R.string.eth_with_amount,
                formattedTxValue
            )
        )
    }
}

@Composable
@Preview
fun MempoolCardPreview() {
    val state = MempoolState("5163", "5", "79,21")
    MempoolCard(state)
}