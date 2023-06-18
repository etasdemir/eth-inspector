package com.etasdemir.ethinspector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.etasdemir.ethinspector.ui.account.AccountScreen
import com.etasdemir.ethinspector.ui.components.EthInspectorLanguage
import com.etasdemir.ethinspector.ui.theme.EthInspectorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EthInspectorLanguage {
                EthInspectorTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
//                        HomeScreen()
//                        InvalidSearchScreen()
//                        WalletScreen()
                        AccountScreen()
//                        SavedItemScreen(type = SavedItemScreen.TRANSACTION)
//                        TransactionDetailScreen()
//                        BlockDetailScreen()
//                        AddressDetailScreen()
//                        ContractDetailScreen()
                    }
                }
            }
        }
    }
}