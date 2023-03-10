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
import com.etasdemir.ethinspector.utils.ColoredAmountText
import com.etasdemir.ethinspector.utils.clip

data class TransferItemState(
    val to: String,
    val tokenName: String,
    val amount: Double,
    val block: String,
    val date: String,
    val onClick: () -> Unit
)

@Composable
fun TransferItem(state: TransferItemState) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30))
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = state.onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = stringResource(id = R.string.to_short),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = state.to.clip(6),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                ColoredAmountText(amount = state.amount)
            }
            CardRowItem(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                field = stringResource(id = R.string.token),
                value = state.tokenName
            )
            CardRowItem(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                field = stringResource(id = R.string.block),
                value = state.block
            )
        }
        ArrowIcon()
    }
}

@Composable
@Preview
fun TransferItemPreview() {
    val state = TransferItemState(
        "0xA78SD6A8S6C87F87S6DF8S7DF6ASD987AS9D",
        "Maker (MKR)",
        1532.112323,
        "530430240",
        "21.02.2020 13:57:45"
    ) {}
    TransferItem(state)
}