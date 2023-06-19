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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.AddressToken
import com.etasdemir.ethinspector.ui.components.ArrowIcon
import com.etasdemir.ethinspector.ui.components.CardRowItem
import com.etasdemir.ethinspector.utils.clip
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun TokenItem(state: AddressToken, onItemClick: (String) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(30))
        .background(MaterialTheme.colorScheme.primary)
        .clickable { onItemClick(state.tokenAddress) }
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
                text = "${state.name} (${state.symbol})",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            CardRowItem(
                field = stringResource(id = R.string.address), value = state.tokenAddress.clip(8)
            )
            CardRowItem(
                field = stringResource(id = R.string.quantity),
                value = BigDecimal(state.quantity)
                    .setScale(2, RoundingMode.UP)
                    .toPlainString()
            )
        }
        ArrowIcon()
    }
}

@Preview
@Composable
fun TokenItemPreview() {
    val state = AddressToken(
        "0X12837987HG12JGH12GH3F89FS7",
        "Furucombo",
        "COMBO",
        "0X12837987HG12JGH12GH3F89FS7",
        128318942893.0
    )
    TokenItem(state, {})
}