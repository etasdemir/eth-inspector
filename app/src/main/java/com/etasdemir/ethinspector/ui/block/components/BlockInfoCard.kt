package com.etasdemir.ethinspector.ui.block.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.*
import com.etasdemir.ethinspector.utils.clip
import timber.log.Timber

data class BlockInfoCardState(
    val blockNumber: String,
    val time: String,
    val transactionCount: Int,
    val miner: String,
    val gasLimit: String,
    val gasUsed: String,
    val baseFeePerGas: String,
)

@Composable
fun BlockInfoCard(state: BlockInfoCardState) {
    val onMinerAddressClick = remember {
        { _: String ->
            Timber.e("navigate to address screen with: ${state.miner}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .background(MaterialTheme.colorScheme.background)
            .padding(25.dp)
    ) {
        CardColumnItem(
            title = stringResource(id = R.string.block_number),
            body = "#${state.blockNumber}"
        )
        CardRowItem(field = stringResource(id = R.string.time), value = state.time)
        CardRowItem(
            field = stringResource(id = R.string.transactions),
            value = state.transactionCount.toString()
        )
        CardRowItem(
            field = stringResource(id = R.string.miner),
            rightContent = {
                UnderlinedButton(
                    text = state.miner.clip(10),
                    onClick = onMinerAddressClick
                )
            })
        CardRowItem(field = stringResource(id = R.string.gas_limit), value = state.gasLimit)
        CardRowItem(field = stringResource(id = R.string.gas_used), value = state.gasUsed)
        CardRowItem(
            field = stringResource(id = R.string.base_fee_per_gas),
            value = stringResource(id = R.string.gwei_with_amount, state.baseFeePerGas)
        )
    }
}

@Composable
@Preview
fun BlockInfoCardPreview() {
    val state = BlockInfoCardState(
        "5301512",
        "21.02.2020 13:57:45",
        52,
        "0xdd1092d0921d8120909xe812091209d91280",
        "30,000,000",
        "10,992,074 | 36.6%",
        "14.418428466"
    )
    BlockInfoCard(state)
}