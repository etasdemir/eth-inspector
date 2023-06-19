package com.etasdemir.ethinspector.ui.saved_tx_and_block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.account.component.*
import com.etasdemir.ethinspector.ui.components.BackButton
import com.etasdemir.ethinspector.ui.components.SimpleTopBar
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import com.etasdemir.ethinspector.ui.theme.Feint

enum class SavedItemType {
    TRANSACTION, BLOCK
}

@Composable
fun SavedItemScreen(type: SavedItemType, navigationHandler: NavigationHandler) {
    val savedItems = listOf<Any>(
        SavedTransactionState(
            "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
            0.035412312,
            "2165403",
            "2 Jan, 2018",
            "12:54:11"
        ) {},
        SavedTransactionState(
            "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
            0.035412312,
            "2165403",
            "2 Jan, 2018",
            "12:54:11"
        ) {},
        SavedTransactionState(
            "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
            0.035412312,
            "2165403",
            "3 Jan, 2018",
            "12:54:11"
        ) {}
    )

    val topBarTitle: String?
    if (type == SavedItemType.TRANSACTION) {
        topBarTitle = stringResource(id = R.string.account_settings_saved_tx)
        // TODO retrieve saved transaction entities
    } else {
        topBarTitle = stringResource(id = R.string.account_settings_saved_blocks)
        // TODO retrieve saved block entities
    }

    var lastRenderedDate = ""
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
            items(savedItems.count()) { index: Int ->
                val item = savedItems[index]
                val date: String
                if (type == SavedItemType.TRANSACTION) {
                    item as SavedTransactionState
                    date = item.date
                    RenderDate(prevDate = lastRenderedDate, currentDate = date)
                    SavedTransactionItem(state = item)
                } else {
                    item as SavedBlockState
                    date = item.date
                    RenderDate(prevDate = lastRenderedDate, currentDate = date)
                    SavedBlockItem(state = item)
                }
                lastRenderedDate = date
            }
        }
    }
}

@Composable
private fun RenderDate(prevDate: String, currentDate: String) {
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
