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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.mappers.*
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.home.components.*
import com.etasdemir.ethinspector.ui.search.SearchTopBar
import timber.log.Timber

@Preview
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    val scrollState: ScrollState = rememberScrollState(0)
    val searchIcon = remember { Icons.Filled.Search }
    val ethStatsResponse by homeViewModel.ethStatsResult.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = "fetch_home_eth_stats") {
        homeViewModel.getEthStats()
    }

    // TODO parsing should be in view model
    var mainStatsState: EthMainStatsState? = null
    var allTimeStatsState: AllTimeStatsState? = null
    var mempoolStatsState: MempoolState? = null
    var dailyStatsState: DailyStatsState? = null
    var ercTokenStatsState: TokenStatsState? = null
    var nftTokenStatsState: TokenStatsState? = null

    if (ethStatsResponse is UIResponseState.Success &&
        ethStatsResponse.data != null &&
        ethStatsResponse.data?.data != null
    ) {
        val ethStats = ethStatsResponse.data!!.data!!
        val layer2 = ethStats.layer2
        mainStatsState = mapEthStatsToMainStatsState(ethStats)
        allTimeStatsState = mapEthStatsToAllTimeStatsState(ethStats)
        mempoolStatsState = mapEthStatsToMempoolStatsState(ethStats)
        dailyStatsState = mapEthStatsToDailyStatsState(ethStats)
        if (layer2 != null) {
            if (layer2.erc_20 != null) {
                ercTokenStatsState = mapEthStatsToTokenStatsState(layer2.erc_20)
            }
            if (layer2.erc_721 != null) {
                nftTokenStatsState = mapEthStatsToTokenStatsState(layer2.erc_721)
            }
        }
    } else {
        Timber.e("HomeScreen error: ${ethStatsResponse.errorMessage}")
    }

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
            if (mainStatsState != null) EthMainStatsCard(mainStatsState)
            if (allTimeStatsState != null) AllTimeStatsCard(allTimeStatsState)
            if (mempoolStatsState != null) MempoolCard(mempoolStatsState)
            if (dailyStatsState != null) DailyStatsCard(dailyStatsState)
            if (ercTokenStatsState != null) TokenStatsCard(
                stringResource(id = R.string.title_erc_20),
                ercTokenStatsState
            )
            if (nftTokenStatsState != null) TokenStatsCard(
                stringResource(id = R.string.title_erc_721),
                nftTokenStatsState
            )
        }
    }
}