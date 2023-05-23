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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.utils.RequestState

@Composable
fun SearchTopBar(
    uneditableText: String? = null,
    onButtonClick: ((searchText: String) -> Unit)? = null,
    searchIcon: ImageVector,
    searchViewModel: SearchViewModel = viewModel()
) {
    var searchText by remember { mutableStateOf("") }
    val isSearchEnabled = uneditableText == null
    val searchUIState by searchViewModel.searchResult.collectAsStateWithLifecycle()

    val onTextChange = remember {
        { newText: String ->
            searchText = newText
        }
    }

    val onSearchButtonClick = remember {
        {
            if (isSearchEnabled) {
                if (searchUIState.state != RequestState.LOADING) {
                    searchViewModel.searchText(searchText)
                }
            } else {
                searchText = ""
                // TODO navigate to home
            }
        }
    }

    if (searchUIState.state == RequestState.SUCCESS) {
        when (searchUIState.type) {
            RawTextType.TRANSACTION -> {
                // Navigate to transaction search with data
            }
            RawTextType.ADDRESS -> {
                // Navigate to transaction search with data
            }
            RawTextType.BLOCK -> {
                // Navigate to transaction search with data
            }
            else -> {
                // Navigate to invalid search screen
            }
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

    Column {
        SearchTopBar(onButtonClick = click, searchIcon = searchIcon)
        Divider(Modifier.height(10.dp))
        SearchTopBar(text, click, closeIcon)
    }
}