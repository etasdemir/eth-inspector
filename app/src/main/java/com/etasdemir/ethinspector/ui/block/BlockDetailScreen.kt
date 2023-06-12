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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.block.components.BlockInfoCard
import com.etasdemir.ethinspector.ui.block.components.BlockTransactionItem
import com.etasdemir.ethinspector.ui.components.DetailTopBar
import com.etasdemir.ethinspector.ui.components.DetailTopBarState
import com.etasdemir.ethinspector.ui.theme.Feint
import timber.log.Timber

@Composable
@Preview
fun BlockDetailScreen(blockViewModel: BlockDetailViewModel = viewModel()) {
    val topBarTitle = stringResource(id = R.string.block_details)
    val blockNumberFromArgs = "17372699"
    val blockState by blockViewModel.blockState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = "initialize_block_detail") {
        blockViewModel.getBlockDetailByNumber(blockNumberFromArgs)
    }

    val onTransactionClick = remember {
        { txHash: String ->
            Timber.e("navigate to tx detail: $txHash")
        }
    }


    if (blockState is UIResponseState.Loading) {
        // Show loading
        Timber.e("BlockDetailScreen: Loading transaction detail screen")
        return
    }
    if (blockState is UIResponseState.Error) {
        Timber.e("BlockDetailScreen: Error ${blockState.errorMessage}")
        return
    }
    if (blockState is UIResponseState.Success && blockState.data == null) {
        Timber.e("BlockDetailScreen: Response is success but data null.")
        return
    }

    // TODO Static data
    val topBarState = remember {
        DetailTopBarState(
            topBarTitle,
            blockState.data!!.isFavourite,
            { previous, now ->

            },
            blockNumberFromArgs
        )
    }

    Scaffold(topBar = { DetailTopBar(state = topBarState) }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(20.dp)
        ) {
            item(3) {
                BlockInfoCard(blockState.data!!)
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
            items(blockState.data!!.transactions) { itemBlockState ->
                BlockTransactionItem(
                    modifier = Modifier.padding(vertical = 5.dp),
                    blockTransaction = itemBlockState,
                    onClick = onTransactionClick
                )
            }
        }
    }
}