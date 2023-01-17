package com.etasdemir.ethinspector.ui.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.CardRowItem

@Composable
@Preview
fun DailyStatsCard() {
    // TODO delete later
    val transactions = "940481"
    val blocks = "7163"
    val burned24h = "1,313.71"
    val largestTxValue = "59,363,201"
    val volume = "1,313,850"
    val avgTxFee = "2.01"

    RoundedTitleCard(title = stringResource(id = R.string.daily_statistics)) {
        CardRowItem(field = stringResource(id = R.string.transactions), value = transactions)
        CardRowItem(field = stringResource(id = R.string.blocks), value = blocks)
        CardRowItem(
            field = stringResource(id = R.string.burned_24h),
            value = stringResource(id = R.string.eth_with_amount, burned24h)
        )
        CardRowItem(
            field = stringResource(id = R.string.largest_tx_value),
            value = stringResource(id = R.string.usd_with_amount, largestTxValue)
        )
        CardRowItem(
            field = stringResource(id = R.string.volume),
            value = stringResource(id = R.string.eth_with_amount, volume)
        )
        CardRowItem(
            field = stringResource(id = R.string.avg_tx_fee),
            value = stringResource(id = R.string.usd_with_amount, avgTxFee)
        )
    }
}