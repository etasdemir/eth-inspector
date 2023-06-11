package com.etasdemir.ethinspector.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.components.DetailTopBar
import com.etasdemir.ethinspector.ui.components.DetailTopBarState
import com.etasdemir.ethinspector.ui.transaction.components.TransactionDetailCard
import com.etasdemir.ethinspector.ui.transaction.components.TransactionInfoCard
import timber.log.Timber

@Composable
@Preview
fun TransactionDetailScreen(
    transactionDetailViewModel: TransactionDetailViewModel = viewModel()
) {
    val scrollState = remember { ScrollState(0) }
    val topBarTitle = stringResource(id = R.string.transaction_detail)
    val transactionState by transactionDetailViewModel.transactionState.collectAsStateWithLifecycle()

    val txHashTakenFromArgs = "0xbc78ab8a9e9a0bca7d0321a27b2c03addeae08ba81ea98b03cd3dd237eabed44"

    LaunchedEffect(key1 = "tx_detail_screen_get_tx") {
        transactionDetailViewModel.getTransactionByHash(txHashTakenFromArgs)
    }

    if (transactionState is UIResponseState.Loading) {
        // Show loading
        Timber.e("TransactionDetailScreen: Loading transaction detail screen")
        return
    }
    if (transactionState is UIResponseState.Error) {
        Timber.e("TransactionDetailScreen: Error ${transactionState.errorMessage}")
        return
    }
    if (transactionState is UIResponseState.Success && transactionState.data == null) {
        Timber.e("TransactionDetailScreen: Response is success but data null.")
        return
    }

    // TODO Static data
    val topBarState = remember {
        DetailTopBarState(
            topBarTitle,
            transactionState.data!!.isFavourite,
            { previous, now ->

            },
            txHashTakenFromArgs
        )
    }

    Scaffold(topBar = { DetailTopBar(topBarState) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(it)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.surface)
                .padding(20.dp)
        ) {
            TransactionInfoCard(transactionState.data!!)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            TransactionDetailCard(transactionState.data!!)
        }
    }
}
