package com.etasdemir.ethinspector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.etasdemir.ethinspector.ui.components.EthInspectorLanguage
import com.etasdemir.ethinspector.ui.contract.ContractDetailScreen
import com.etasdemir.ethinspector.ui.shared.SharedAccountViewModel
import com.etasdemir.ethinspector.ui.theme.EthInspectorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sharedAccountViewModel by viewModels<SharedAccountViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EthInspectorLanguage(sharedAccountViewModel) {
                EthInspectorTheme(sharedAccountViewModel) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
//                        HomeScreen()
//                        InvalidSearchScreen()
//                        WalletScreen()
//                        AccountScreen(sharedAccountViewModel)
//                        SavedItemScreen(type = SavedItemScreen.TRANSACTION)
//                        TransactionDetailScreen()
//                        BlockDetailScreen()
//                        AddressDetailScreen()
                        ContractDetailScreen()
                    }
                }
            }
        }
    }
}