package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CardRowItem(
    modifier: Modifier = Modifier,
    field: String? = null,
    value: String? = null,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxWidth()) {
        if (leftContent != null) {
            leftContent()
        } else if (field != null) {
            Text(modifier = Modifier, text = field, color = MaterialTheme.colorScheme.tertiary)
        }
        if (rightContent != null) {
            rightContent()
        } else if (value != null) {
            Text(modifier = Modifier, text = value, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
@Preview
fun CardRowItemPreview() {
    val field = "Title"
    val value = "Value"
    CardRowItem(field = field, value = value)
}