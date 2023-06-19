package com.etasdemir.ethinspector.ui.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.CardRowItem
import com.etasdemir.ethinspector.utils.*
import java.math.RoundingMode

data class DailyStatsState(
    val transactions: Double,
    val blocks: Double,
    val burned24h: String,
    val largestTxValue: String,
    val volume: String,
    val avgTxFee: Double
)

@Composable
fun DailyStatsCard(state: DailyStatsState) {
    val formatted24Burned = remember {
        state.burned24h
            .fromWei(EthUnit.ETHER)
            .setScale(2, RoundingMode.UP)
            .toPlainString()
    }
    val formattedLargestTxValue = remember {
        state.largestTxValue
            .format(0)
            .addDots()
    }
    val formattedVolume = remember {
        state.volume
            .fromWei(EthUnit.ETHER)
            .setScale(0, RoundingMode.UP)
            .toPlainString()
            .addDots()
    }

    RoundedTitleCard(title = stringResource(id = R.string.daily_statistics)) {
        CardRowItem(
            field = stringResource(id = R.string.transactions),
            value = state.transactions.toString().format(0)
        )
        CardRowItem(
            field = stringResource(id = R.string.blocks),
            value = state.blocks.toString().format(0)
        )
        CardRowItem(
            field = stringResource(id = R.string.burned_24h),
            value = stringResource(
                id = R.string.eth_with_amount,
                formatted24Burned
            )
        )
        CardRowItem(
            field = stringResource(id = R.string.largest_tx_value),
            value = stringResource(id = R.string.usd_with_amount, formattedLargestTxValue)
        )
        CardRowItem(
            field = stringResource(id = R.string.volume),
            value = stringResource(id = R.string.eth_with_amount, formattedVolume)
        )
        CardRowItem(
            field = stringResource(id = R.string.avg_tx_fee),
            value = stringResource(id = R.string.usd_with_amount, state.avgTxFee.format(2))
        )
    }
}

@Composable
@Preview
fun DailyStatsCardPreview() {
    val state = DailyStatsState(
        940481.0, 7163.0, "1,313.71",
        "59,363,201", "1,313,850", 2.01
    )
    DailyStatsCard(state)
}
