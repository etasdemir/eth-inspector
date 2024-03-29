package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FavouriteButton(
    initialIsFavourite: Boolean,
    onFavouriteClick: (prev: Boolean, now: Boolean) -> Unit
) {
    var isFavourite by remember {
        mutableStateOf(initialIsFavourite)
    }
    val favIcon = if (isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
    val onClick = remember {
        {
            val previous = isFavourite
            isFavourite = !previous
            onFavouriteClick(previous, isFavourite)
        }
    }

    IconButton(onClick = onClick, modifier = Modifier.size(36.dp)) {
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
        val onFavouriteClick = { _: Boolean, _: Boolean -> }
        FavouriteButton(true, onFavouriteClick)
        Divider()
        FavouriteButton(false, onFavouriteClick)
    }
}
