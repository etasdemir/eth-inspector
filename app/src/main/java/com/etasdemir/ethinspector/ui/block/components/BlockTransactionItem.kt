package com.etasdemir.ethinspector.ui.block.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.BlockTransaction
import com.etasdemir.ethinspector.utils.*

@Composable
fun BlockTransactionItem(
    modifier: Modifier = Modifier,
    blockTransaction: BlockTransaction,
    onClick: (txHash: String) -> Unit
) {
    val amount = remember {
        blockTransaction.amount.toString().fromWei(EthUnit.ETHER).toString().format(6)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(40))
            .background(MaterialTheme.colorScheme.background)
            .clickable { onClick(blockTransaction.address) }
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = blockTransaction.address.clip(12),
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
    val state = BlockTransaction(
        12390103289123u,
        "0x1656AFA45AF5765F76F4F187567F85",
        7165918000000000u
    )
    BlockTransactionItem(blockTransaction = state) {}
}