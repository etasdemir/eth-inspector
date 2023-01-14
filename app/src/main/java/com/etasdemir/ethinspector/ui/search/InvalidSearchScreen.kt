package com.etasdemir.ethinspector.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvalidSearchScreen() {
    val searchIcon = remember { Icons.Filled.Close }
    // TODO Take from args
    val searchedValue = "Some wrong address"

    // TODO Check for saved value too.
    val isDarkTheme = isSystemInDarkTheme()
    val searchNotFoundImg = remember {
        if (isDarkTheme)
            R.drawable.search_not_found_dark
        else
            R.drawable.search_not_found_light
    }

    val onCloseClick = remember {
        { _: String ->
            Timber.e("navigate to home screen")
        }
    }


    Scaffold(topBar = { SearchTopBar(searchedValue, onCloseClick, searchIcon) }) {
        Column(modifier = Modifier.padding(it)) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = searchNotFoundImg),
                contentDescription = "Search not found",
            )
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                text = stringResource(id = R.string.search_invalid_search_title),
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
            Text(
                modifier = Modifier.padding(top = 5.dp, end = 20.dp, bottom = 20.dp, start = 20.dp),
                text = stringResource(id = R.string.search_invalid_search_desc),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp
            )
        }
    }
}


@Preview
@Composable
fun InvalidSearchScreenPreview() {
    InvalidSearchScreen()
}
