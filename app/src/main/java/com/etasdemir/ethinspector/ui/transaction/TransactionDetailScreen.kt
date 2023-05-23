package com.etasdemir.ethinspector.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.DetailTopBar
import com.etasdemir.ethinspector.ui.components.DetailTopBarState
import com.etasdemir.ethinspector.ui.transaction.components.*

@Composable
fun TransactionDetailScreen() {
    val scrollState = remember { ScrollState(0) }
    val topBarTitle = stringResource(id = R.string.transaction_detail)

    // TODO Static data
    val topBarState = remember {
        DetailTopBarState(
            topBarTitle,
            true,
            {},
            "tx hash"
        )
    }
    val infoState = TransactionInfoCardState(
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        "21.02.2020 13:57:45",
        "5301614",
        0.0123151,
        "0.00012531"
    )
    val detailState = TransactionDetailCardState(
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        "21999",
        "14.418428466",
        "16.418428466",
        "2 (EIP-1559)",
        40351.0
    )

    Scaffold(topBar = { DetailTopBar(topBarState) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(it)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.surface)
                .padding(20.dp)
        ) {
            TransactionInfoCard(infoState)
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))
            TransactionDetailCard(detailState)
        }
    }
}

@Composable
@Preview
fun TransactionDetailScreenPreview() {
    TransactionDetailScreen()
}
