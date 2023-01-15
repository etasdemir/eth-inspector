package com.etasdemir.ethinspector.ui.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.SimpleTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen() {
    // TODO delete later
    val anyWalletSaved = remember { false }

    Scaffold(topBar = {
        SimpleTopBar(title = stringResource(id = R.string.my_wallets))
    }) {
        if (anyWalletSaved) {
            LazyColumn(
                modifier = Modifier.padding(
                    top = it.calculateTopPadding() + 4.dp,
                    end = 4.dp,
                    bottom = it.calculateBottomPadding(),
                    start = 4.dp
                )
            ) {

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