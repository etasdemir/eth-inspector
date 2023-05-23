package com.etasdemir.ethinspector.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.home.components.*
import com.etasdemir.ethinspector.ui.search.SearchTopBar
import timber.log.Timber

@Preview
@Composable
fun HomeScreen() {
    val scrollState: ScrollState = rememberScrollState(0)
    val searchIcon = remember { Icons.Filled.Search }

    val onSearchClick = remember {
        { searchText: String ->
            Timber.e(
                "search given text: $searchText. " +
                        "if result found navigate to detail screen " +
                        "else navigate to invalid search screen"
            )
        }
    }

    Scaffold(topBar = {
        SearchTopBar(
            onButtonClick = onSearchClick,
            searchIcon = searchIcon
        )
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
            EthMainStatsCard()
            AllTimeStatsCard()
            MempoolCard()
            DailyStatsCard()
            TokenStatsCard(stringResource(id = R.string.title_erc_20))
            TokenStatsCard(stringResource(id = R.string.title_erc_721))
        }
    }
}