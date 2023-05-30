package com.etasdemir.ethinspector.ui.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.CardRowItem
import com.etasdemir.ethinspector.utils.addDots
import com.etasdemir.ethinspector.utils.humanReadableByteCountSI

data class AllTimeStatsState(
    val blocks: String, val blockchainSize: String, val transactions: String, val calls: String
)

@Composable
fun AllTimeStatsCard(state: AllTimeStatsState) {
    val convertedBlockchainSize = humanReadableByteCountSI(state.blockchainSize.toLong())

    RoundedTitleCard(title = stringResource(id = R.string.all_time)) {
        CardRowItem(field = stringResource(id = R.string.blocks), value = state.blocks.addDots())
        CardRowItem(
            field = stringResource(id = R.string.blockchain_size),
            value = convertedBlockchainSize
        )
        CardRowItem(
            field = stringResource(id = R.string.transactions),
            value = state.transactions.addDots()
        )
        CardRowItem(field = stringResource(id = R.string.calls), value = state.calls.addDots())
    }
}


@Composable
@Preview
fun AllTimeStatsCardPreview() {
    val state = AllTimeStatsState(
        "16214027", "646015837469", "1,814,252,328", "6,228,353,636"
    )
    AllTimeStatsCard(state)
}