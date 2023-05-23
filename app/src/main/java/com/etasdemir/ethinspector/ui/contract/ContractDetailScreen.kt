package com.etasdemir.ethinspector.ui.contract

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.*
import com.etasdemir.ethinspector.ui.contract.components.ContractInfoColumn
import com.etasdemir.ethinspector.ui.contract.components.ContractInfoColumnState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ContractDetailScreen() {
    val topBarTitle = stringResource(id = R.string.contract_details)

    // TODO static data
    val isFavourite = remember { true }

    val address = "0x123123KHGADSD87SD6ZXC3AH1SDA2SD87AIDS68"
    val creatorAddress = "0x123123KHGADSD87SD6ZXC3AH1SDA2SD87AIDS68"
    val creationTime = "2021-08-30 14:01:29"
    val txCount = 67321.0
    val balanceEth = "29.1273293897"
    val balanceUsd = "35123.1238123"
    val contractInfoState = remember {
        ContractInfoColumnState(
            address, creatorAddress, creationTime, txCount, balanceEth, balanceUsd
        )
    }

    val topBarState = DetailTopBarState(
        topBarTitle,
        isFavourite,
        {},
        address
    )

    val txItemState = listOf(
        AddressTransactionItemState(
            "0x9868768A6SD86A87ASD6A8S787A66S87D6A8",
            4232.3030,
            "142353532",
            "21.02.2020",
            "15:23:17"
        ) {},
        AddressTransactionItemState(
            "0x9868768A6SD86A87ASD6A8S787A66S87D6A8",
            4232.3030,
            "142353532",
            "21.02.2020",
            "15:23:17"
        ) {}
    )

    Scaffold(topBar = { DetailTopBar(state = topBarState) }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            item {
                ContractInfoColumn(state = contractInfoState)
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                    text = stringResource(id = R.string.transactions),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(txItemState) { transaction ->
                AddressTransactionItem(state = transaction)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}