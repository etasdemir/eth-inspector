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
import com.etasdemir.ethinspector.utils.AddressType
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
    // TODO static data
    val address = "0x388C818CA8B9251b393131C08a736A67ccB19297"
    val topBarTitle = stringResource(id = R.string.contract_details)

    val topBarState by contractViewModel.topBarState.collectAsStateWithLifecycle()
    val isSheetShown by contractViewModel.isSheetShown.collectAsStateWithLifecycle()
    val contractDetailState by contractViewModel.contractDetailState.collectAsStateWithLifecycle()
    val data = contractDetailState.data

    val onTransactionClick = remember {
        { txHash: String ->
            Timber.e("navigate to transaction: $txHash")
        }
    }

    LaunchedEffect(key1 = "initialize_contract_detail") {
        contractViewModel.initialize(address, topBarTitle, AddressType.CONTRACT)
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
    if (contractDetailState is UIResponseState.Success && data == null) {
        Timber.e("ContractDetailScreen: Response is success but data null.")
        return
    }


    Scaffold(topBar = {
        if (topBarState != null) {
            DetailTopBar(
                state = topBarState!!
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
            if (data!!.contractInfo != null) {
                item {
                    ContractInfoColumn(state = data.contractInfo!!, address)
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
    if (isSheetShown) {
        AddressSaveModal(contractViewModel.modalState)
    }
}