package com.etasdemir.ethinspector.ui.account.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.FeintText
import com.etasdemir.ethinspector.utils.ColoredAmountText
import com.etasdemir.ethinspector.utils.clip

data class SavedTransactionState(
    val transactionHash: String,
    val amount: Double,
    val block: String,
    val date: String,
    val time: String,
    val onItemClick: () -> Unit
)

@Composable
fun SavedTransactionItem(state: SavedTransactionState) {
    val clippedTxHash = remember {
        state.transactionHash.clip(10)
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = state.onItemClick)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = clippedTxHash,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 18.sp
            )
            ColoredAmountText(amount = state.amount)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.account_settings_block, state.block),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FeintText(text = state.date)
                FeintText(text = state.time)
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp
        )
    }
}

@Composable
@Preview
fun SavedTransactionItemPreview() {
    val state = SavedTransactionState(
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        0.03253677751,
        "2165403",
        "2 Jan, 2018",
        "12:54:11"
    ) {}
    SavedTransactionItem(state)
}