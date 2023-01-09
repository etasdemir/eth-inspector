package com.etasdemir.ethinspector.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.home.components.*
import com.etasdemir.ethinspector.ui.search.SearchTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen() {
    val scrollState: ScrollState = rememberScrollState(0)

    Scaffold(topBar = { SearchTopBar() }) { padding ->
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
            EthMainStatsCard()
            AllTimeStatsCard()
            MempoolCard()
            DailyStatsCard()
            TokenStatsCard(stringResource(id = R.string.title_erc_20))
            TokenStatsCard(stringResource(id = R.string.title_erc_721))
        }
    }
}