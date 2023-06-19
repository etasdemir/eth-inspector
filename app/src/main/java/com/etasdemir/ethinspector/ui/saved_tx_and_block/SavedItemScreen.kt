package com.etasdemir.ethinspector.ui.saved_tx_and_block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.BackButton
import com.etasdemir.ethinspector.ui.components.SimpleTopBar
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import com.etasdemir.ethinspector.ui.saved_tx_and_block.components.SavedBlockItem
import com.etasdemir.ethinspector.ui.saved_tx_and_block.components.SavedTransactionItem
import com.etasdemir.ethinspector.ui.theme.Feint
import java.text.SimpleDateFormat
import java.util.Locale

enum class SavedItemType {
    TRANSACTION, BLOCK
}

@Composable
fun SavedItemScreen(
    type: SavedItemType,
    navigationHandler: NavigationHandler,
    viewModel: SavedItemViewModel = hiltViewModel()
) {
    val topBarTitle: String = if (type == SavedItemType.TRANSACTION) {
        stringResource(id = R.string.account_settings_saved_tx)
    } else {
        stringResource(id = R.string.account_settings_saved_blocks)
    }

    val savedBlocksState by viewModel.savedBlocksState.collectAsStateWithLifecycle()
    val savedTransactionsState by viewModel.savedTransactionsState.collectAsStateWithLifecycle()

    val onItemClick = remember {
        { arg: String ->
            if (type == SavedItemType.TRANSACTION) {
                navigationHandler.navigateToTransaction(arg)
            } else {
                navigationHandler.navigateToBlock(arg)
            }
        }
    }

    val simpleDateFormat = remember {
        SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
    }

    LaunchedEffect(key1 = type) {
        viewModel.getSavedItems(type)
    }

    var lastItemDate = ""
    Scaffold(topBar = {
        SimpleTopBar(
            title = topBarTitle,
            leadingContent = { BackButton(navigationHandler::popBackStack) })
    }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            if (type == SavedItemType.TRANSACTION) {
                items(savedTransactionsState) { item ->
                    item.timestamp?.let { time ->
                        val date = simpleDateFormat.format(time.toLong() * 1000L)
                        GroupDateAsDaily(prevDate = lastItemDate, currentDate = date)
                        lastItemDate = date
                    }
                    SavedTransactionItem(state = item) {
                        onItemClick(item.transactionHash)
                    }
                }
            } else {
                items(savedBlocksState) { item ->
                    val date = simpleDateFormat.format(item.timestamp.toLong() * 1000L)
                    GroupDateAsDaily(prevDate = lastItemDate, currentDate = date)
                    SavedBlockItem(state = item) {
                        onItemClick(item.blockNumber.toString())
                    }
                    lastItemDate = date
                }
            }
        }
    }
}

@Composable
private fun GroupDateAsDaily(prevDate: String, currentDate: String) {
    if (prevDate != currentDate) {
        Text(
            modifier = Modifier.padding(top = 15.dp),
            text = currentDate,
            color = Feint,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
@Preview
fun SavedItemScreenPreview() {
    val testController = rememberNavController()
    SavedItemScreen(SavedItemType.TRANSACTION, NavigationHandler(testController))
}
