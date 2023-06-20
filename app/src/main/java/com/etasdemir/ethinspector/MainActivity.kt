package com.etasdemir.ethinspector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.etasdemir.ethinspector.ui.components.EthInspectorLanguage
import com.etasdemir.ethinspector.ui.navigation.NavigationGraph
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
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Surface(
                                modifier = Modifier.padding(it),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                val navHostController = rememberNavController()
                                NavigationGraph(
                                    navController = navHostController,
                                    sharedAccountViewModel
                                )
                            }
                        }
                    }
                }
        }
    }
}