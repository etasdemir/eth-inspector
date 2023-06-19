package com.etasdemir.ethinspector.ui.address


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
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.address.components.*
import com.etasdemir.ethinspector.ui.components.*
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import timber.log.Timber

@Composable
fun AddressDetailScreen(
    address: String,
    navigationHandler: NavigationHandler,
    addressViewModel: AddressDetailViewModel = hiltViewModel(),
) {
    val topBarTitle = stringResource(id = R.string.address_details)

    val topBarState by addressViewModel.topBarState.collectAsStateWithLifecycle()
    val isSheetShown by addressViewModel.isSheetShown.collectAsStateWithLifecycle()
    val addressState by addressViewModel.addressState.collectAsStateWithLifecycle()

    val onTransferItemClick = remember {
        { hash: String ->
            Timber.e("AddressDetailScreen::onTransferItemClick $hash")
        }
    }

    val onTransactionItemClick = remember {
        { txHash: String ->
            navigationHandler.navigateToTransaction(txHash)
        }
    }

    val onTokenItemClick = remember {
        { address: String ->
            navigationHandler.navigateToContract(address)
        }
    }

    LaunchedEffect(key1 = "initialize_account_detail") {
        addressViewModel.initialize(address, topBarTitle, AddressType.ACCOUNT)
        addressViewModel.getAccountInfoByHash(address)
    }

    if (addressState is UIResponseState.Loading) {
        // Show loading
        Timber.e("AddressDetailScreen: Loading address detail screen")
        return
    }
    if (addressState is UIResponseState.Error) {
        Timber.e("AddressDetailScreen: Error ${addressState.errorMessage}")
        navigationHandler.popBackStack()
        return
    }
    if (addressState is UIResponseState.Success && addressState.data == null) {
        Timber.e("AddressDetailScreen: Response is success but data null.")
        return
    }
    val data = addressState.data!!

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
                AddressInfoColumn(address, data.accountInfo)
            }
            item {
                if (data.addressTokens.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                        text = stringResource(id = R.string.tokens),
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            items(data.addressTokens) { token ->
                TokenItem(state = token, onTokenItemClick)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }
            item {
                if (data.transactions.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                        text = stringResource(id = R.string.transactions),
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            items(data.transactions) { item ->
                if (item.transactionHash != null) {
                    AddressTransactionItem(item, onTransactionItemClick)
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                }
            }
            item {
                if (data.transfers.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                        text = stringResource(id = R.string.transfers),
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            items(data.transfers) { transfer ->
                TransferItem(state = transfer, onTransferItemClick)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }
        }
    }
    if (isSheetShown) {
        AddressSaveModal(addressViewModel.modalState)
    }
}