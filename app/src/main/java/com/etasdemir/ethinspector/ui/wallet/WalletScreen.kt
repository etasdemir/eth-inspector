package com.etasdemir.ethinspector.ui.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen() {
    Scaffold(topBar = {
        Column {
            Text(
                modifier = Modifier.padding(15.dp),
                text = stringResource(id = R.string.my_wallets),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleLarge
            )
            Divider(Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.tertiary)
        }
    }) {
        LazyColumn(
            modifier = Modifier.padding(
                top = it.calculateTopPadding() + 4.dp,
                end = 4.dp,
                bottom = it.calculateBottomPadding(),
                start = 4.dp
            )
        ) {
            
        }
    }
}

@Composable
@Preview
fun WalletScreenPreview() {
    WalletScreen()
}