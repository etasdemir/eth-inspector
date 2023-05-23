package com.etasdemir.ethinspector.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.ui.theme.Feint

@Composable
fun FeintText(text: String) {
    Text(text = text, color = Feint, fontSize = 15.sp)
}