package com.etasdemir.ethinspector.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun BodyTitleText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.tertiary,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}