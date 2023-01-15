package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FavouriteButton(isFavourite: Boolean, onFavouriteClick: () -> Unit) {
    val favIcon = remember {
        if (isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
    }

    IconButton(onClick = onFavouriteClick, modifier = Modifier.size(36.dp)) {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = favIcon,
            contentDescription = "Add to favourites",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
@Preview
fun FavouriteButtonPreview() {
    Column {
        val onFavouriteClick = { }
        FavouriteButton(true, onFavouriteClick)
        Divider()
        FavouriteButton(false, onFavouriteClick)
    }
}
