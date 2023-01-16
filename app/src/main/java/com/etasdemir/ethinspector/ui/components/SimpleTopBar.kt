package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SimpleTopBar(title: String, leadingContent: (@Composable () -> Unit)? = null) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (leadingContent != null) leadingContent()
            Text(
                text = title,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Divider(Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.tertiary)
    }
}

@Composable
@Preview
fun SimpleTopBarPreview() {
    SimpleTopBar(title = "Simple Top Bar")
}