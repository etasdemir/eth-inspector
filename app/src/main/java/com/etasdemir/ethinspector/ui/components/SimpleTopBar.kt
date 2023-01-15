package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SimpleTopBar(title: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(15.dp),
            text = title,
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.titleLarge
        )
        Divider(Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.tertiary)
    }
}

@Composable
@Preview
fun SimpleTopBarPreview() {
    SimpleTopBar(title = "Simple Top Bar")
}