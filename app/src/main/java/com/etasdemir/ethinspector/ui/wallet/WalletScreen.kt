package com.etasdemir.ethinspector.ui.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.AddressType
import com.etasdemir.ethinspector.ui.components.SimpleTopBar
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import com.etasdemir.ethinspector.ui.saved_tx_and_block.SavedWalletItem
import com.etasdemir.ethinspector.ui.saved_tx_and_block.SavedWalletState
import com.etasdemir.ethinspector.utils.EthUnit
import com.etasdemir.ethinspector.utils.fromWei

@Composable
fun WalletScreen(
    navigationHandler: NavigationHandler,
    walletViewModel: WalletViewModel = hiltViewModel()
) {
    val savedAddressesState by walletViewModel.savedAddresses.collectAsStateWithLifecycle()
    val isAnyAddressSaved =
        (savedAddressesState != null) &&
                (savedAddressesState!!.savedAccounts.isNotEmpty() ||
                        savedAddressesState!!.savedAccounts.isNotEmpty())

    val onWalletItemClick = remember {
        { address: String, addressType: AddressType ->
            if (addressType == AddressType.CONTRACT) {
                navigationHandler.navigateToContract(address)
            } else if (addressType == AddressType.ACCOUNT) {
                navigationHandler.navigateToAccount(address)
            }
        }
    }

    Scaffold(topBar = {
        SimpleTopBar(title = stringResource(id = R.string.my_wallets))
    }) {
        if (isAnyAddressSaved) {
            LazyColumn(
                modifier = Modifier.padding(it),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(savedAddressesState!!.savedAccounts) { account ->
                    SavedWalletItem(
                        state = SavedWalletState(
                            addressName = account.accountInfo.userGivenName,
                            address = account.accountInfo.accountAddress,
                            ethBalance = account.accountInfo.balanceWei.fromWei(EthUnit.ETHER),
                            usdBalance = account.accountInfo.balanceUsd.toString(),
                            AddressType.ACCOUNT
                        ), onItemClick = onWalletItemClick
                    )
                }
                items(savedAddressesState!!.savedContracts) { contract ->
                    SavedWalletItem(
                        state = SavedWalletState(
                            addressName = contract.contractInfoEntity.userGivenName,
                            address = contract.contractInfoEntity.contractAddress,
                            ethBalance = contract.contractInfoEntity.balanceWei.fromWei(EthUnit.ETHER),
                            usdBalance = contract.contractInfoEntity.balanceUsd.toString(),
                            AddressType.CONTRACT
                        ), onItemClick = onWalletItemClick
                    )
                }
            }
        } else {
            NoSavedWallet()
        }
    }
}

@Composable
@Preview
fun WalletScreenPreview() {
    val testController = rememberNavController()
    WalletScreen(NavigationHandler(testController))
}