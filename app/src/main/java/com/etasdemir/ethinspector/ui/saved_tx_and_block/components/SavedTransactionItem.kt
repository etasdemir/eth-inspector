package com.etasdemir.ethinspector.ui.saved_tx_and_block.components

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
import com.etasdemir.ethinspector.data.domain_model.Transaction
import com.etasdemir.ethinspector.ui.components.FeintText
import com.etasdemir.ethinspector.utils.*
import java.math.BigDecimal

@Composable
fun SavedTransactionItem(state: Transaction, onItemClick: () -> Unit) {
    val clippedTxHash = remember {
        state.transactionHash.clip(10)
    }
    val convertedDate =
        if (state.timestamp != null) getDateString(state.timestamp.toLong()) else null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
    ) {
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
            ColoredAmountText(amount = BigDecimal(state.amount), digits = 6)
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
            if (convertedDate != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FeintText(text = convertedDate)
                }
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
    val state = Transaction(
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        "1723891240123",
        4123908123u,
        0.03253677751,
        2165403.0,
        "2 Jan, 2018",
        "12:54:11",
        123908123.0,
        123908123.0,
        123908123.0,
        1,
        "123908",
        true
    )
    SavedTransactionItem(state) {}
}