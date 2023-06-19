package com.etasdemir.ethinspector.ui.saved_tx_and_block.components

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
import com.etasdemir.ethinspector.data.domain_model.Block
import com.etasdemir.ethinspector.ui.components.FeintText
import com.etasdemir.ethinspector.utils.clip
import com.etasdemir.ethinspector.utils.getDateString

@Composable
fun SavedBlockItem(state: Block, onItemClick: () -> Unit) {
    val clippedMinerAdr = remember {
        state.minerAddress.clip(6)
    }
    val blockCreationDate = remember { getDateString(state.timestamp.toLong()) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onItemClick)) {
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
                    state.txCount
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
                FeintText(text = blockCreationDate)
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
    val state = Block(
        2165403u,
        "123124123123",
        10230,
        "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
        1245123123u,
        12314212u,
        123123131u,
        emptyList(),
        true
    )
    SavedBlockItem(state) {}
}