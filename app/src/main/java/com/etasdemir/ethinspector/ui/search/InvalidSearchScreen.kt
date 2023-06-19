package com.etasdemir.ethinspector.ui.search

import androidx.compose.foundation.Image
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
import androidx.navigation.compose.rememberNavController
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.AvailableThemes
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import com.etasdemir.ethinspector.ui.theme.LocalTheme

@Composable
fun InvalidSearchScreen(
    searchedValue: String,
    navigationHandler: NavigationHandler
) {
    val searchIcon = remember { Icons.Filled.Close }
    val isDarkTheme = LocalTheme.current == AvailableThemes.Dark
    val searchNotFoundImg = remember {
        if (isDarkTheme)
            R.drawable.search_not_found_dark
        else
            R.drawable.search_not_found_light
    }

    Scaffold(topBar = {
        SearchTopBar(
            uneditableText = searchedValue,
            searchIcon = searchIcon,
            navigationHandler = navigationHandler
        )
    }) {
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
    val testHost = rememberNavController()
    InvalidSearchScreen("Some wrong address", NavigationHandler(testHost))
}
