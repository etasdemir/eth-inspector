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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.TokenStats
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.home.components.*
import com.etasdemir.ethinspector.ui.search.SearchTopBar
import timber.log.Timber

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
    homeViewModel: HomeViewModel = viewModel()
) {
    val scrollState: ScrollState = rememberScrollState(0)
    val searchIcon = remember { Icons.Filled.Search }
    val ethStats by homeViewModel.ethStats.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = "initialize_home") {
        homeViewModel.getEthStats()
    }

    if (ethStats is UIResponseState.Loading) {
        // Show loading
        Timber.e("HomeScreen: Loading transaction detail screen")
        return
    }
    if (ethStats is UIResponseState.Error) {
        Timber.e("HomeScreen: Error ${ethStats.errorMessage}")
        return
    }
    if (ethStats is UIResponseState.Success && ethStats.data == null) {
        Timber.e("HomeScreen: Response is success but data null.")
        return
    }
    val stats = ethStats.data!!
    val ethStatsState = homeViewModel.mapEthStatsToState(stats)

    Scaffold(topBar = {
        SearchTopBar(searchIcon = searchIcon)
    }) { padding ->
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
                    stringResource(id = R.string.title_erc_20),
                    ethStatsState.ercTokenStatsState
                )
            }
            if (ethStatsState.nftTokenStatsState != null) {
                TokenStatsCard(
                    stringResource(id = R.string.title_erc_721),
                    ethStatsState.nftTokenStatsState
                )
            }
        }
    }
}