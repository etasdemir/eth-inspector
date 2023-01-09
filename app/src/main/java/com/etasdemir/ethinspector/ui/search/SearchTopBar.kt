package com.etasdemir.ethinspector.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchTopBar() {
    var searchText by remember { mutableStateOf("") }

    val onTextChange = remember {
        { newText: String ->
            searchText = newText
        }
    }

    val onSearchClick = remember {
        {
            Timber.e("search given text: $searchText")
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
            onValueChange = onTextChange,
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_placeholder),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onBackground,
                placeholderColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.tertiary
            )
        )
        IconButton(
            modifier = Modifier
                .padding(start = 10.dp)
                .size(50.dp)
                .clip(RoundedCornerShape(50)),
            onClick = onSearchClick,
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}