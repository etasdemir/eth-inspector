package com.etasdemir.ethinspector.ui.address


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.address.components.*
import com.etasdemir.ethinspector.ui.components.*
import timber.log.Timber

data class AddressDetailState(
    val addressInfo: AddressInfoColumnState,
    val transactions: List<AddressTransactionItemState>,
    val tokens: List<TokenItemState>,
    var transfers: List<TransferItemState>
)

@Composable
@Preview
fun AddressDetailScreen(
    addressViewModel: AddressDetailViewModel = viewModel()
) {
    // TODO static data
    val address = "0x31A47094C6325D357c7331c621d6768Ba041916e"
    val addressState by addressViewModel.addressDetailState.collectAsStateWithLifecycle()

    val isAddressFavourite = false
    val topBarTitle = stringResource(id = R.string.address_details)
    val topBarState = remember {
        DetailTopBarState(
            barTitle = topBarTitle,
            isFavouriteEnabled = isAddressFavourite,
            onFavouriteClick = {},
            textToCopy = address
        )
    }

    val onTransferItemClick = remember {
        { hash: String ->
            Timber.e("AddressDetailScreen::onTransferItemClick $hash")
        }
    }

    val onTransactionItemClick = remember {
        { txHash: String ->
            Timber.e("navigate to transaction detail with $txHash")
        }
    }

    val onTokenItemClick = remember {
        { address: String ->
            Timber.e("navigate to token detail with $address")
        }
    }

    LaunchedEffect(key1 = "get_address_detail_by_hash") {
        addressViewModel.getAccountInfoByHash(address)
    }

    if (addressState is UIResponseState.Loading) {
        // Show loading
        Timber.e("AddressDetailScreen: Loading transaction detail screen")
        return
    }
    if (addressState is UIResponseState.Error) {
        Timber.e("AddressDetailScreen: Error ${addressState.errorMessage}")
        return
    }
    if (addressState is UIResponseState.Success && addressState.data == null) {
        Timber.e("AddressDetailScreen: Response is success but data null.")
        return
    }

    val data = addressState.data!!

    Scaffold(topBar = { DetailTopBar(state = topBarState) }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            item {
                AddressInfoColumn(address, data.addressInfo)
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                    text = stringResource(id = R.string.tokens),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(data.tokens) { token ->
                TokenItem(state = token, onTokenItemClick)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                    text = stringResource(id = R.string.transactions),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleLarge
                )
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
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                    text = stringResource(id = R.string.transfers),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleLarge
                )
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
}