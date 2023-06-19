package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BackButton(navigateBack: () -> Unit) {
    val onBackClick = remember {
        {
            navigateBack()
        }
    }

    IconButton(onClick = onBackClick, modifier = Modifier.size(36.dp)) {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back button",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
@Preview
fun BackButtonPreview() {
    BackButton(){}
}