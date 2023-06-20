package com.etasdemir.ethinspector.ui.contract

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.AddressType
import com.etasdemir.ethinspector.ui.components.*
import com.etasdemir.ethinspector.ui.contract.components.ContractInfoColumn
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import com.etasdemir.ethinspector.ui.shared.UIResponseHandler

@Composable
fun ContractDetailScreen(
    address: String,
    navigationHandler: NavigationHandler,
    contractViewModel: ContractDetailViewModel = hiltViewModel()
) {
    val topBarTitle = stringResource(id = R.string.contract_details)

    val topBarState by contractViewModel.topBarState.collectAsStateWithLifecycle()
    val isSheetShown by contractViewModel.isSheetShown.collectAsStateWithLifecycle()
    val contractState by contractViewModel.contractState.collectAsStateWithLifecycle()

    val onTransactionClick = remember {
        { txHash: String ->
            navigationHandler.navigateToTransaction(txHash)
        }
    }

    LaunchedEffect(key1 = "initialize_contract_detail") {
        contractViewModel.initialize(address, topBarTitle, AddressType.CONTRACT)
        contractViewModel.getContractDetailByHash(address)
    }

    UIResponseHandler(state = contractState, navigationHandler = navigationHandler) {contract ->
        Scaffold(topBar = {
            if (topBarState != null) {
                DetailTopBar(
                    state = topBarState!!,
                    navigateBack = navigationHandler::popBackStack
                )
            }
        }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(24.dp)
            ) {
                item {
                    ContractInfoColumn(
                        state = contract.contractInfo,
                        address,
                        navigationHandler::navigateToAccount
                    )
                    Text(
                        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                        text = stringResource(id = R.string.transactions),
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                items(contract.transactions) { transaction ->
                    if (transaction.transactionHash != null) {
                        AddressTransactionItem(state = transaction, onTransactionClick)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
        if (isSheetShown) {
            AddressSaveModal(contractViewModel.modalState)
        }
    }
}