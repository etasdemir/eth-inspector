package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardColumnItem(
    title: String? = null,
    body: String? = null,
    titleContent: (@Composable () -> Unit)? = null,
    bodyContent: (@Composable () -> Unit)? = null
) {
    if (titleContent != null) {
        titleContent()
    } else if (title != null) {
        BodyTitleText(text = title)
    }
    if (bodyContent != null) {
        bodyContent()
    } else if (body != null) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = body,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}