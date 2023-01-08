package com.etasdemir.ethinspector.ui.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.etasdemir.ethinspector.R

@Composable
fun TokenStatsCard(title: String) {
    val tokens = "570,931"
    val newTokens = "591"
    val tx = "1,233,157,345"
    val newTx = "734,074"
    
    RoundedTitleCard(title) {
        CardRowItem(field = stringResource(id = R.string.tokens), value = tokens)
        CardRowItem(field = stringResource(id = R.string.new_tokens_24h), value = newTokens)
        CardRowItem(field = stringResource(id = R.string.transactions), value = tx)
        CardRowItem(field = stringResource(id = R.string.new_tx_24h), value = newTx)
    }
}