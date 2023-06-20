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
import com.etasdemir.ethinspector.ui.components.DetailTopBar
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import com.etasdemir.ethinspector.ui.shared.UIResponseHandler
import com.etasdemir.ethinspector.ui.transaction.components.TransactionDetailCard
import com.etasdemir.ethinspector.ui.transaction.components.TransactionInfoCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            coroutineScope.launch(Dispatchers.Main) {
                val isContract = transactionDetailViewModel.isAddressContract(address)
                if (isContract) {
                    navigationHandler.navigateToContract(address)
                } else {
                    navigationHandler.navigateToAccount(address)
                }
            }
        }
    }

    UIResponseHandler(
        state = transactionState,
        navigationHandler = navigationHandler
    ) { transaction ->
        Scaffold(topBar = {
            DetailTopBar(
                topBarState!!,
                navigateBack = navigationHandler::popBackStack
            )
        }) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(it)
                    .verticalScroll(scrollState)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(20.dp)
            ) {
                TransactionInfoCard(transaction, navigationHandler::navigateToBlock)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
                TransactionDetailCard(transaction) { address: String ->
                    onAddressClick(
                        address
                    )
                }
            }
        }
    }
}
