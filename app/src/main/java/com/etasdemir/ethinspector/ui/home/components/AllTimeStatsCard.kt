package com.etasdemir.ethinspector.ui.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.CardRowItem

@Composable
@Preview
fun AllTimeStatsCard() {
    // TODO delete later.
    val blocks = "16214027"
    val size = "521 GB"
    val transactions = "1,814,252,328"
    val calls = "6,228,353,636"

    RoundedTitleCard(title = stringResource(id = R.string.all_time)) {
        CardRowItem(field = stringResource(id = R.string.blocks), value = blocks)
        CardRowItem(field = stringResource(id = R.string.blockchain_size), value = size)
        CardRowItem(field = stringResource(id = R.string.transactions), value = transactions)
        CardRowItem(field = stringResource(id = R.string.calls), value = calls)
    }
}