package com.etasdemir.ethinspector.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.FeintText
import com.etasdemir.ethinspector.utils.clip

data class SavedBlockState(
    val blockNumber: String,
    val transactionCount: Int,
    val minerAddress: String,
    val date: String,
    val time: String,
    val onItemClick: () -> Unit
)

@Composable
fun SavedBlockItem(state: SavedBlockState) {
    val clippedMinerAdr = remember {
        state.minerAddress.clip(6)
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = state.onItemClick)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "#${state.blockNumber}",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 16.sp
            )
            FeintText(
                text = stringResource(
                    id = R.string.account_settings_tx_count,
                    state.transactionCount
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FeintText(text = stringResource(id = R.string.account_settings_miner, clippedMinerAdr))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FeintText(text = state.date)
                FeintText(text = state.time)
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp
        )
    }
}

@Preview
@Composable
fun SavedBlockItemPreview() {
    val state = SavedBlockState(
        "2165403",
        341,
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        "2 Jan, 2018",
        "12:54:11"
    ) {}
    SavedBlockItem(state)
}