package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutlinedButtonComponent(onClick: () -> Unit, text: String) {
    val buttonColors = ButtonDefaults.outlinedButtonColors(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.tertiary
    )
    OutlinedButton(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 40.dp, vertical = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
        colors = buttonColors
    ) {
        Text(
            text = text,
            fontSize = 14.sp
        )
    }
}