package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun UnderlinedButton(modifier: Modifier = Modifier, text: String, onClick: (value: String) -> Unit) {
    Surface(
        modifier = modifier
            .clickable { onClick(text) },
        color = Color.Transparent
    )
    {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.tertiary,
            style = TextStyle(
                textDecoration = TextDecoration.Underline
            ),
            fontSize = 16.sp
        )
    }
}