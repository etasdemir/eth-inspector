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
import com.etasdemir.ethinspector.ui.block.BlockDetailState
import com.etasdemir.ethinspector.ui.components.*
import com.etasdemir.ethinspector.utils.*
import timber.log.Timber

@Composable
fun BlockInfoCard(state: BlockDetailState) {
    val onMinerAddressClick = remember {
        { _: String ->
            Timber.e("navigate to address screen with: ${state.minerAddress}")
        }
    }
    val blockCreationDate = remember { getDateString(state.timestamp.toLong()) }
    val usedGasPercentage = remember {
        (state.gasUsed * 100u).div(state.gasLimit)
    }
    val usedGasPercentageRes =
        stringResource(id = R.string.percentage_with_value, usedGasPercentage)
    val gasLimitStr = remember {
        state.gasLimit.toString().addDots()
    }
    val gasLimitStrRes = stringResource(id = R.string.wei_with_amount, gasLimitStr)
    val gasUsedStr = remember {
        state.gasUsed.toString().addDots()
    }
    val gasUsedStrRes = stringResource(id = R.string.wei_with_amount, gasUsedStr)
    val baseFeePerGas = remember {
        state.baseFeePerGas.toString().fromWei(EthUnit.GWEI).toString().format(3)
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
        CardRowItem(field = stringResource(id = R.string.time), value = blockCreationDate)
        CardRowItem(
            field = stringResource(id = R.string.transactions),
            value = state.txCount.toString()
        )
        CardRowItem(
            field = stringResource(id = R.string.miner)
        ) {
            UnderlinedButton(
                text = state.minerAddress.clip(10),
                onClick = onMinerAddressClick
            )
        }
        CardRowItem(field = stringResource(id = R.string.gas_limit), value = gasLimitStrRes)
        CardRowItem(
            field = stringResource(id = R.string.gas_used),
            value = "$gasUsedStrRes | $usedGasPercentageRes"
        )
        CardRowItem(
            field = stringResource(id = R.string.base_fee_per_gas),
            value = stringResource(
                id = R.string.gwei_with_amount,
                baseFeePerGas
            )
        )
    }
}

@Composable
@Preview
fun BlockInfoCardPreview() {
    val state = BlockDetailState(
        5301512u,
        "12412315235235",
        52,
        "0x5a0b54d5dc17e0aadc383d2db43b0a0d3e029c4c",
        30058619u,
        6226794u,
        24962883652u,
        emptyList()
    )
    BlockInfoCard(state)
}