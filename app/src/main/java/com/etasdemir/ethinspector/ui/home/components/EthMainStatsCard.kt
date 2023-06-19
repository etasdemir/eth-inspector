package com.etasdemir.ethinspector.ui.home.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.etasdemir.ethinspector.data.domain_model.AvailableThemes
import com.etasdemir.ethinspector.ui.theme.LocalTheme
import com.etasdemir.ethinspector.utils.*
import java.math.RoundingMode
import kotlin.math.abs

data class EthMainStatsState(
    val marketPrice: Double,
    val priceChange: Double,
    val circulation: String,
    val marketCap: String,
    val dominance: Double
)

private val previewState = EthMainStatsState(
    1184.19123123,
    0.51231241235,
    "115018182780730000000000000",
    "142710238976",
    16.934534,
)

@Composable
@Preview
fun EthMainStatsCard(state: EthMainStatsState = previewState) {
    val isDarkTheme = LocalTheme.current == AvailableThemes.Dark
    val ethIconResId = if (isDarkTheme) R.drawable.ic_eth_dark else R.drawable.ic_eth_light
    val priceChangeStr = remember {
        val change = state.priceChange
        val absChange = abs(change)
        if (change < 0) {
            "-${absChange.format(2)}"
        } else {
            "+${absChange.format(2)}"
        }
    }

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
            Text(text = stringResource(id = R.string.usd_with_amount, state.marketPrice.format(2)))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = priceChangeStr)
        }
        HomeMainRowElement(
            title = stringResource(id = R.string.coin_circulation),
            description = stringResource(
                id = R.string.eth_with_amount,
                state.circulation
                    .fromWei(EthUnit.ETHER)
                    .setScale(0, RoundingMode.UP)
                    .toPlainString()
                    .addDots()
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        HomeMainRowElement(
            title = stringResource(id = R.string.coin_market_cap),
            description = stringResource(id = R.string.usd_with_amount, state.marketCap.addDots()),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        HomeMainRowElement(
            title = stringResource(id = R.string.coin_dominance),
            description = "${state.dominance.format(2)}%",
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