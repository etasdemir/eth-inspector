package com.etasdemir.ethinspector.ui.saved_tx_and_block

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.AddressType
import com.etasdemir.ethinspector.ui.components.ArrowIcon
import com.etasdemir.ethinspector.utils.clip
import java.math.BigDecimal
import java.math.RoundingMode

data class SavedWalletState(
    val addressName: String?,
    val address: String,
    val ethBalance: BigDecimal,
    val usdBalance: String,
    val addressType: AddressType
)

@Composable
fun SavedWalletItem(state: SavedWalletState, onItemClick: (String, AddressType) -> Unit) {
    val clippedAddress = remember {
        state.address.clip(12)
    }
    val ethBalance = remember { state.ethBalance.setScale(6, RoundingMode.UP) }
    val balance =
        "${
            stringResource(
                id = R.string.eth_with_amount,
                ethBalance.toPlainString()
            )
        } (${stringResource(id = R.string.usd_with_amount, state.usdBalance)})"


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = { onItemClick(state.address, state.addressType) })
            .padding(vertical = 20.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (state.addressName != null) {
                Text(
                    text = state.addressName,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            HorizontalItem(
                text = stringResource(id = R.string.address_type),
                value = state.addressType.name
            )
            HorizontalItem(
                text = stringResource(id = R.string.wallet_saved_address),
                value = clippedAddress
            )
            HorizontalItem(
                text = stringResource(id = R.string.wallet_saved_balance),
                value = balance
            )
        }
        ArrowIcon()
    }
}

@Composable
private fun HorizontalItem(text: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = text,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp
        )
    }
}

@Composable
@Preview
fun SavedWalletItemPreview() {
    val state = SavedWalletState(
        "My address",
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        BigDecimal(29.077655),
        "34.253.19",
        AddressType.CONTRACT
    )
    SavedWalletItem(state) { _: String, _: AddressType -> }
}