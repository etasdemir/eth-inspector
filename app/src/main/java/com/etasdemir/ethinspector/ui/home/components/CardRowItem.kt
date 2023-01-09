package com.etasdemir.ethinspector.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CardRowItem(field: String, value: String, modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxWidth()) {
        Text(modifier = Modifier, text = field, color = MaterialTheme.colorScheme.tertiary)
        Text(modifier = Modifier, text = value)
    }
}