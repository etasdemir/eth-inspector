package com.etasdemir.ethinspector.ui.block.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.utils.*

data class BlockTransactionItemState(
    val address: String,
    val amount: String,
    val onClick: (String) -> Unit
)

@Composable
fun BlockTransactionItem(modifier: Modifier = Modifier, state: BlockTransactionItemState) {
    val amount = state.amount.format(digits = 5)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(40))
            .background(MaterialTheme.colorScheme.background)
            .clickable { state.onClick(state.address) }
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = state.address.clip(12),
            color = MaterialTheme.colorScheme.tertiary,
            style = TextStyle(
                textDecoration = TextDecoration.Underline
            ),
            fontSize = 16.sp
        )
        Text(
            text = stringResource(id = R.string.eth_with_amount, amount),
            color = amountColor(amount.toDouble())
        )
    }
}

@Composable
@Preview
fun BlockTransactionItemPreview() {
    val state = BlockTransactionItemState(
        "0x1656AFA45AF5765F76F4F187567F85",
        "0.05012035123"
    ) {}
    BlockTransactionItem(state = state)
}