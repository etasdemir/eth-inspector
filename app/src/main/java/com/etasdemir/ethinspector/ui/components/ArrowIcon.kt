package com.etasdemir.ethinspector.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.etasdemir.ethinspector.R

@Composable
fun ArrowIcon(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = R.drawable.arrow_forward_ios_24),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onBackground
    )
}