package com.etasdemir.ethinspector.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.*
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import timber.log.Timber

@Composable
fun SearchTopBar(
    uneditableText: String? = null,
    onButtonClick: ((searchText: String) -> Unit)? = null,
    searchIcon: ImageVector,
    navigationHandler: NavigationHandler,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    var searchText by remember { mutableStateOf("") }
    val isSearchEnabled = uneditableText == null
    val searchResultPairState by searchViewModel.searchResult.collectAsStateWithLifecycle()
    var isSearching by remember {
        mutableStateOf(false)
    }

    val onTextChange = remember {
        { newText: String ->
            searchText = newText
        }
    }

    val onSearchButtonClick = remember {
        {
            if (isSearchEnabled && searchText.length > 2) {
                if (searchResultPairState == null) {
                    isSearching = true
                    searchViewModel.searchText(searchText)
                }
            } else {
                searchText = ""
                navigationHandler.navigateToHome()
            }
        }
    }

    LaunchedEffect(key1 = searchResultPairState) {
        if (searchResultPairState != null) {
            isSearching = false
            val response = searchResultPairState?.second
            val data = searchResultPairState?.second?.data

            if (response is UIResponseState.Success && data != null) {
                when (searchResultPairState!!.first) {
                    SearchType.TRANSACTION -> {
                        val txHash = (data as Transaction).transactionHash
                        navigationHandler.navigateToTransaction(txHash)
                    }

                    SearchType.ACCOUNT -> {
                        val accountAddress = (data as Account).accountInfo.accountAddress
                        navigationHandler.navigateToAccount(accountAddress)
                    }

                    SearchType.CONTRACT -> {
                        val contractAddress = (data as Contract).contractInfo.contractAddress
                        navigationHandler.navigateToContract(contractAddress)
                    }

                    SearchType.BLOCK -> {
                        val blockNumber = (data as Block).blockNumber
                        navigationHandler.navigateToBlock(blockNumber.toString())
                    }

                    else -> {
                        navigationHandler.navigateToInvalidSearch(searchText)
                    }
                }
            } else if (searchResultPairState?.second is UIResponseState.Error) {
                val errorMessage = searchResultPairState!!.second.errorMessage
                Timber.e("Error response: $errorMessage")
                navigationHandler.navigateToInvalidSearch(searchText)
            }
            searchViewModel.resetSearchResult()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(35)),
            value = searchText,
            enabled = isSearchEnabled,
            onValueChange = onTextChange,
            singleLine = true,
            placeholder = {
                Text(
                    text = uneditableText ?: stringResource(id = R.string.search_placeholder),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.tertiary
            )
        )
        IconButton(
            modifier = Modifier
                .padding(start = 10.dp)
                .size(50.dp)
                .clip(RoundedCornerShape(50)),
            onClick = {
                onSearchButtonClick()
                if (onButtonClick != null) {
                    onButtonClick(searchText)
                }
            },
            enabled = !isSearching,
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = searchIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
fun SearchTopBarPreview() {
    val click = { _: String -> }
    val text = "Some wrong address"
    val searchIcon = remember { Icons.Filled.Search }
    val closeIcon = remember { Icons.Filled.Close }
    val testController = rememberNavController()
    val scope = rememberCoroutineScope()

    Column {
        SearchTopBar(
            onButtonClick = click,
            searchIcon = searchIcon,
            navigationHandler = NavigationHandler(testController, scope)
        )
        Divider(Modifier.height(10.dp))
        SearchTopBar(text, click, closeIcon, navigationHandler = NavigationHandler(testController, scope))
    }
}