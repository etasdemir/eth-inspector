package com.etasdemir.ethinspector.ui.address.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.ArrowIcon
import com.etasdemir.ethinspector.ui.components.CardRowItem
import com.etasdemir.ethinspector.utils.clip

data class TokenItemState(
    val name: String,
    val address: String,
    val quantity: String,
    val onClick: (address: String) -> Unit,
)

@Composable
fun TokenItem(state: TokenItemState) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(30))
        .background(MaterialTheme.colorScheme.primary)
        .clickable { state.onClick(state.address) }
        .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = state.name,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleMedium
            )
            CardRowItem(
                field = stringResource(id = R.string.address), value = state.address.clip(8)
            )
            CardRowItem(
                field = stringResource(id = R.string.quantity), value = state.quantity
            )
        }
        ArrowIcon()
    }
}

@Preview
@Composable
fun TokenItemPreview() {
    val state = TokenItemState(
        "Furucombo (COMBO)", "0X12837987HG12JGH12GH3F89FS7", "12831894289312831200"
    ) {}
    TokenItem(state)
}