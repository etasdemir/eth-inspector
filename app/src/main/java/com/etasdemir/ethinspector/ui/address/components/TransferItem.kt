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
import com.etasdemir.ethinspector.data.domain_model.AddressTransfer
import com.etasdemir.ethinspector.ui.components.ArrowIcon
import com.etasdemir.ethinspector.ui.components.CardRowItem
import com.etasdemir.ethinspector.utils.ColoredAmountText
import com.etasdemir.ethinspector.utils.clip
import java.math.BigDecimal


@Composable
fun TransferItem(state: AddressTransfer, onItemClick: (String) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30))
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = { onItemClick(state.hash) })
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
                    if (state.to != null) {
                        Text(
                            text = state.to.clip(6),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                ColoredAmountText(amount = BigDecimal(state.amount), digits = 6)
            }
            CardRowItem(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                field = stringResource(id = R.string.token),
                value = state.tokenName
            )
            CardRowItem(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                field = stringResource(id = R.string.block),
                value = state.blockNumber
            )
        }
        ArrowIcon()
    }
}

@Composable
@Preview
fun TransferItemPreview() {
    val state = AddressTransfer(
        "0xA78SD6A8S6C87F87S6DF8S7DF6ASD987AS9D",
        "0xA78SD6A8S6C87F87S6DF8S7DF6ASD987AS9D",
        "Maker",
        "MKR",
        1532.112323,
        "530430240",
        "530430240"
    )
    TransferItem(state) {}
}