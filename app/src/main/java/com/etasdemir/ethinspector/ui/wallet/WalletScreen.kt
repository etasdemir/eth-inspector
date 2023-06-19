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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.AddressType
import com.etasdemir.ethinspector.ui.components.SimpleTopBar
import com.etasdemir.ethinspector.utils.EthUnit
import com.etasdemir.ethinspector.utils.fromWei
import timber.log.Timber

@Composable
fun WalletScreen(
    walletViewModel: WalletViewModel = viewModel()
) {
    val savedAddressesState by walletViewModel.savedAddresses.collectAsStateWithLifecycle()
    val isAnyAddressSaved =
        (savedAddressesState != null) &&
                (savedAddressesState!!.savedAccounts.isNotEmpty() ||
                        savedAddressesState!!.savedAccounts.isNotEmpty())

    val onWalletItemClick = remember {
        { address: String, addressType: AddressType ->
            Timber.e("on wallet item click $address type: $addressType")
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
    WalletScreen()
}