package com.etasdemir.ethinspector.ui.contract

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
import com.etasdemir.ethinspector.ui.components.*
import com.etasdemir.ethinspector.ui.contract.components.ContractInfoColumn
import com.etasdemir.ethinspector.ui.contract.components.ContractInfoColumnState
import timber.log.Timber

data class ContractDetailState(
    val contractInfo: ContractInfoColumnState?,
    val transactions: List<AddressTransactionItemState>
)

@Composable
@Preview
fun ContractDetailScreen(
    contractViewModel: ContractDetailViewModel = viewModel()
) {
    val topBarTitle = stringResource(id = R.string.contract_details)
    val contractDetailState by contractViewModel.contractDetailState.collectAsStateWithLifecycle()

    // TODO static data
    val address = "0x388C818CA8B9251b393131C08a736A67ccB19297"
    val isFavourite = remember { true }

    val topBarState = DetailTopBarState(
        topBarTitle,
        isFavourite,
        {},
        address
    )

    val onTransactionClick = remember {
        { txHash: String ->
            Timber.e("navigate to transaction: $txHash")
        }
    }

    LaunchedEffect(key1 = "get_contract_detail_by_hash") {
        contractViewModel.getContractDetailByHash(address)
    }

    if (contractDetailState is UIResponseState.Loading) {
        // Show loading
        Timber.e("ContractDetailScreen: Loading transaction detail screen")
        return
    }
    if (contractDetailState is UIResponseState.Error) {
        Timber.e("ContractDetailScreen: Error ${contractDetailState.errorMessage}")
        return
    }
    if (contractDetailState is UIResponseState.Success && contractDetailState.data == null) {
        Timber.e("ContractDetailScreen: Response is success but data null.")
        return
    }
    val data = contractDetailState.data!!

    Scaffold(topBar = { DetailTopBar(state = topBarState) }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            if (data.contractInfo != null) {
                item {
                    ContractInfoColumn(state = data.contractInfo, address)
                    Text(
                        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                        text = stringResource(id = R.string.transactions),
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            items(data.transactions) { transaction ->
                if (transaction.transactionHash != null) {
                    AddressTransactionItem(state = transaction, onTransactionClick)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}