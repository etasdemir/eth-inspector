package com.etasdemir.ethinspector.ui.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.CardRowItem
import com.etasdemir.ethinspector.utils.addDots

data class TokenStatsState(
    val tokens: String,
    val newTokens: String,
    val tx: String,
    val newTx: String,
)

@Composable
fun TokenStatsCard(title: String, state: TokenStatsState) {
    RoundedTitleCard(title) {
        CardRowItem(field = stringResource(id = R.string.tokens), value = state.tokens.addDots())
        CardRowItem(
            field = stringResource(id = R.string.new_tokens_24h),
            value = state.newTokens.addDots()
        )
        CardRowItem(field = stringResource(id = R.string.transactions), value = state.tx.addDots())
        CardRowItem(field = stringResource(id = R.string.new_tx_24h), value = state.newTx.addDots())
    }
}

@Composable
@Preview
fun TokenStatsCardPreview() {
    val title = "ERC-20 Tokens Stats"
    val state = TokenStatsState(
        "570,931", "591",
        "1,233,157,345", "734,074"
    )
    TokenStatsCard(title, state)
}
