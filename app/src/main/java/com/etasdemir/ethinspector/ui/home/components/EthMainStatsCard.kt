package com.etasdemir.ethinspector.ui.home.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R

@Composable
@Preview
fun EthMainStatsCard() {
    // TODO Delete later
    val marketPrice = 1184.19
    val priceChange = "+0.55"
    val circulation = "122,375,301"
    val marketCap = "142,710,238,976"
    val dominance = "16.94%"

    // TODO Check for saved value too.
    val isDarkTheme = isSystemInDarkTheme()
    val ethIconResId = if (isDarkTheme) R.drawable.ic_eth_dark else R.drawable.ic_eth_light

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.background)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Image(
            painter = painterResource(ethIconResId),
            contentDescription = "Ethereum icon",
            modifier = Modifier
                .padding(bottom = 8.dp, top = 10.dp)
                .size(60.dp)
        )
        Text(
            text = stringResource(id = R.string.ethereum),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(text = stringResource(id = R.string.usd_with_amount, marketPrice))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = priceChange)
        }
        HomeMainRowElement(
            title = stringResource(id = R.string.coin_circulation),
            description = stringResource(id = R.string.eth_with_amount, circulation),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        HomeMainRowElement(
            title = stringResource(id = R.string.coin_market_cap),
            description = stringResource(id = R.string.usd_with_amount, marketCap),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        HomeMainRowElement(
            title = stringResource(id = R.string.coin_dominance),
            description = dominance,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
fun HomeMainRowElement(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, fontWeight = FontWeight.SemiBold)
        Text(text = description)
    }
}