package com.etasdemir.ethinspector.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.components.DetailTopBar
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import com.etasdemir.ethinspector.ui.transaction.components.TransactionDetailCard
import com.etasdemir.ethinspector.ui.transaction.components.TransactionInfoCard
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun TransactionDetailScreen(
    txHash: String,
    navigationHandler: NavigationHandler,
    transactionDetailViewModel: TransactionDetailViewModel = hiltViewModel()
) {
    val scrollState = remember { ScrollState(0) }
    val topBarTitle = stringResource(id = R.string.transaction_detail)
    val transactionState by transactionDetailViewModel.transactionState.collectAsStateWithLifecycle()
    val topBarState by transactionDetailViewModel.topBarState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = "initialize_transaction_screen") {
        transactionDetailViewModel.apply {
            initialize(txHash, topBarTitle)
            getTransactionByHash(txHash)
        }
    }

    val onAddressClick = remember {
        { address: String ->
            coroutineScope.launch {
                val isContract = transactionDetailViewModel.isAddressContract(address)
                if (isContract) {
                    navigationHandler.navigateToContract(address)
                } else {
                    navigationHandler.navigateToAccount(address)
                }
            }
        }
    }

    if (transactionState is UIResponseState.Loading) {
        // Show loading
        Timber.e("TransactionDetailScreen: Loading transaction detail screen")
        return
    }
    if (transactionState is UIResponseState.Error) {
        Timber.e("TransactionDetailScreen: Error ${transactionState.errorMessage}")
        navigationHandler.popBackStack()
        return
    }
    if (transactionState is UIResponseState.Success && transactionState.data == null) {
        Timber.e("TransactionDetailScreen: Response is success but data null.")
        return
    }

    Scaffold(topBar = {
        DetailTopBar(
            topBarState!!,
            navigateBack = navigationHandler::popBackStack
        )
    }) { it ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(it)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.surface)
                .padding(20.dp)
        ) {
            TransactionInfoCard(transactionState.data!!, navigationHandler::navigateToBlock)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            TransactionDetailCard(transactionState.data!!) { address: String ->
                onAddressClick(
                    address
                )
            }
        }
    }
}
