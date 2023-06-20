package com.etasdemir.ethinspector.ui.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.TokenStats
import com.etasdemir.ethinspector.ui.components.CardRowItem
import com.etasdemir.ethinspector.utils.addDots

@Composable
fun TokenStatsCard(title: String, state: TokenStats) {
    RoundedTitleCard(title) {
        if (state.tokens != null) {
            CardRowItem(
                field = stringResource(id = R.string.tokens),
                value = state.tokens.addDots()
            )
        }
        if (state.newTokens != null) {
            CardRowItem(
                field = stringResource(id = R.string.new_tokens_24h),
                value = state.newTokens.addDots()
            )
        }
        if (state.tx != null) {
            CardRowItem(
                field = stringResource(id = R.string.transactions),
                value = state.tx.addDots()
            )
        }
        if (state.newTx != null) {
            CardRowItem(
                field = stringResource(id = R.string.new_tx_24h),
                value = state.newTx.addDots()
            )
        }
    }
}

@Composable
@Preview
fun TokenStatsCardPreview() {
    val title = "ERC-20 Tokens Stats"
    val state = TokenStats(
        "570,931", "591",
        "1,233,157,345", "734,074"
    )
    TokenStatsCard(title, state)
}
