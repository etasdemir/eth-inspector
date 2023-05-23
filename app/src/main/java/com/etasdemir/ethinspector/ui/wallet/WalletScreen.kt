package com.etasdemir.ethinspector.ui.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.SimpleTopBar
import timber.log.Timber

@Composable
fun WalletScreen() {
    val onWalletItemClick = remember {
        {
            Timber.e("on wallet item click")
        }
    }
    // TODO delete later
    val anyWalletSaved = remember { true }
    // TODO retrieve from local source
    val savedWallets = listOf(
        SavedWalletState(
            "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
            29.077655,
            "34.253.19",
            onWalletItemClick
        ),
        SavedWalletState(
            "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
            29.077655,
            "34.253.19",
            onWalletItemClick
        )
    )

    Scaffold(topBar = {
        SimpleTopBar(title = stringResource(id = R.string.my_wallets))
    }) {
        if (anyWalletSaved) {
            LazyColumn(
                modifier = Modifier.padding(it),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(savedWallets) { wallet ->
                    SavedWalletItem(state = wallet)
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