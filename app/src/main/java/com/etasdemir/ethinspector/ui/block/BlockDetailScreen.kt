package com.etasdemir.ethinspector.ui.block

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.block.components.*
import com.etasdemir.ethinspector.ui.components.DetailTopBar
import com.etasdemir.ethinspector.ui.components.DetailTopBarState
import com.etasdemir.ethinspector.ui.theme.Feint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun BlockDetailScreen() {
    val topBarTitle = stringResource(id = R.string.block_details)

    // TODO Static data
    val topBarState = remember {
        DetailTopBarState(
            topBarTitle,
            true,
            {},
            "block number"
        )
    }
    val infoState = BlockInfoCardState(
        "5301512",
        "21.02.2020 13:57:45",
        52,
        "0xdd1092d0921d8120909xe812091209d91280",
        "30,000,000",
        "10,992,074 | 36.6%",
        "14.418428466"
    )
    val blockTransactions = listOf(
        BlockTransactionItemState(
            "0x1656AFA45AF5765F76F4F187567F85",
            "0.05012035123"
        ) {},
        BlockTransactionItemState(
            "0x1656AFA45AF5765F76F4F187567F85",
            "-0.05012035123"
        ) {},
        BlockTransactionItemState(
            "0x1656AFA45AF5765F76F4F187567F85",
            "0.05012035123"
        ) {}
    )

    Scaffold(topBar = { DetailTopBar(state = topBarState) }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(20.dp)
        ) {
            item(3) {
                BlockInfoCard(infoState)
                Text(
                    modifier = Modifier.padding(top = 40.dp),
                    text = stringResource(id = R.string.block_transactions),
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.tx_hash),
                        fontSize = 16.sp,
                        color = Feint
                    )
                    Text(
                        text = stringResource(id = R.string.amount),
                        fontSize = 16.sp,
                        color = Feint
                    )
                }
            }
            items(blockTransactions) { transaction ->
                BlockTransactionItem(
                    modifier = Modifier.padding(vertical = 5.dp),
                    state = transaction
                )
            }
        }
    }
}