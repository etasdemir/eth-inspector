package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun ShareButton() {
    val onShareClick = remember {
        {
            Timber.e("Share with others")
        }
    }

    IconButton(onClick = onShareClick, modifier = Modifier.size(36.dp)) {
        Icon(
            imageVector = Icons.Filled.Share,
            contentDescription = "Share with others",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
@Preview
fun ShareButtonPreview() {
    ShareButton()
}
