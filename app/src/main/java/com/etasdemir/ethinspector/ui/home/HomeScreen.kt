package com.etasdemir.ethinspector.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.etasdemir.ethinspector.data.domain_model.TokenStats
import com.etasdemir.ethinspector.ui.home.components.*
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import com.etasdemir.ethinspector.ui.search.SearchTopBar
import com.etasdemir.ethinspector.ui.shared.UIResponseHandler

data class EthStatsState(
    val mainStatsState: EthMainStatsState,
    val allTimeStatsState: AllTimeStatsState,
    val mempoolStatsState: MempoolState,
    val dailyStatsState: DailyStatsState,
    val ercTokenStatsState: TokenStats?,
    val nftTokenStatsState: TokenStats?
)

@Composable
fun HomeScreen(
    navigationHandler: NavigationHandler,
    bottomBar: @Composable () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scrollState: ScrollState = rememberScrollState(0)
    val searchIcon = remember { Icons.Filled.Search }
    val ethStats by homeViewModel.ethStats.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = "initialize_home") {
        homeViewModel.getEthStats()
    }

    UIResponseHandler(state = ethStats, navigationHandler = navigationHandler) { stats ->
        val ethStatsState = homeViewModel.mapEthStatsToState(stats)
        Scaffold(
            topBar = {
                SearchTopBar(searchIcon = searchIcon, navigationHandler = navigationHandler)
            }, bottomBar = bottomBar
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(
                        top = padding.calculateTopPadding() + 8.dp,
                        end = 20.dp,
                        bottom = padding.calculateBottomPadding() + 8.dp,
                        start = 20.dp,
                    )
                    .verticalScroll(scrollState)
            ) {
                EthMainStatsCard(ethStatsState.mainStatsState)
                AllTimeStatsCard(ethStatsState.allTimeStatsState)
                MempoolCard(ethStatsState.mempoolStatsState)
                DailyStatsCard(ethStatsState.dailyStatsState)
                if (ethStatsState.ercTokenStatsState != null) {
                    TokenStatsCard(
                        stringResource(id = com.etasdemir.ethinspector.R.string.title_erc_20),
                        ethStatsState.ercTokenStatsState
                    )
                }
                if (ethStatsState.nftTokenStatsState != null) {
                    TokenStatsCard(
                        stringResource(id = com.etasdemir.ethinspector.R.string.title_erc_721),
                        ethStatsState.nftTokenStatsState
                    )
                }
            }
        }
    }
}