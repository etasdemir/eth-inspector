package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.utils.*

data class AddressTransactionItemState(
    val transactionHash: String?,
    val amountWei: Double,
    val block: Long,
    val date: String,
)

@Composable
fun AddressTransactionItem(state: AddressTransactionItemState, onItemClick: (String) -> Unit) {
    if (state.transactionHash == null) {
        return
    }
    val clippedTxHash = remember {
        state.transactionHash.clip(6)
    }
    val amountEth = remember {
        state.amountWei.toString().fromWei(EthUnit.ETHER).toString().format(6).toDouble()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(35))
            .clickable(onClick = { onItemClick(state.transactionHash) })
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = clippedTxHash,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 18.sp
                )
                ColoredAmountText(amount = amountEth)
            }
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.account_settings_block, state.block),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 15.sp
                )
                FeintText(text = state.date)
            }
        }
        ArrowIcon()
    }

}


@Composable
@Preview
fun AddressTransactionItemPreview() {
    val state = AddressTransactionItemState(
        "0x9868768A6SD86A87ASD6A8S787A66S87D6A8",
        4232.3030, 142353532, "2023-03-02 02:59:23"
    )
    AddressTransactionItem(state) {}
}