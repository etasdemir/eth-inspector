package com.etasdemir.ethinspector.ui.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.block.components.BlockInfoCard
import com.etasdemir.ethinspector.ui.block.components.BlockTransactionItem
import com.etasdemir.ethinspector.ui.components.DetailTopBar
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import com.etasdemir.ethinspector.ui.shared.UIResponseHandler
import com.etasdemir.ethinspector.ui.theme.Feint

@Composable
fun BlockDetailScreen(
    blockNumber: String,
    navigationHandler: NavigationHandler,
    blockViewModel: BlockDetailViewModel = hiltViewModel()
) {
    val topBarTitle = stringResource(id = R.string.block_details)

    val blockState by blockViewModel.blockState.collectAsStateWithLifecycle()
    val topBarState by blockViewModel.topBarState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = "initialize_block_detail") {
        blockViewModel.apply {
            initialize(blockNumber, topBarTitle)
            getBlockDetailByNumber(blockNumber)
        }
    }

    val onTransactionClick = remember {
        { txHash: String ->
            navigationHandler.navigateToTransaction(txHash)
        }
    }

    UIResponseHandler(state = blockState, navigationHandler = navigationHandler) { block ->
        Scaffold(topBar = {
            DetailTopBar(
                state = topBarState!!,
                navigateBack = navigationHandler::popBackStack
            )
        }) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(20.dp)
            ) {
                item(3) {
                    BlockInfoCard(block, navigationHandler::navigateToAccount)
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
                items(block.transactions) { itemBlockState ->
                    BlockTransactionItem(
                        modifier = Modifier.padding(vertical = 5.dp),
                        blockTransaction = itemBlockState,
                        onClick = onTransactionClick
                    )
                }
            }
        }
    }

}