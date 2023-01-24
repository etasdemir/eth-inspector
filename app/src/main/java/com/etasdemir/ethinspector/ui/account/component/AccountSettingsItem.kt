package com.etasdemir.ethinspector.ui.account.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R

data class AccountSettingsItemState(
    val leadingIcon: Painter,
    val name: String,
    val onItemClick: () -> Unit,
    val rightContent: @Composable () -> Unit
)

@Composable
fun AccountSettingsItem(state: AccountSettingsItemState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = state.onItemClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                painter = state.leadingIcon,
                contentDescription = state.name,
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(text = state.name, fontSize = 18.sp)
        }
        state.rightContent()
    }
}

@Preview
@Composable
fun AccountSettingsItemPreview() {
    val state = AccountSettingsItemState(
        painterResource(id = R.drawable.nights_stay_24),
        "Select theme",
        {}
    ) {}
    AccountSettingsItem(state)
}