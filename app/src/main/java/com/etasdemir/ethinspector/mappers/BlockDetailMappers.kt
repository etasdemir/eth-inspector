package com.etasdemir.ethinspector.mappers

import com.etasdemir.ethinspector.data.remote.dto.etherscan.BlockResponse
import com.etasdemir.ethinspector.data.remote.dto.etherscan.EtherscanRPCResponse
import com.etasdemir.ethinspector.ui.block.BlockDetailState
import com.etasdemir.ethinspector.ui.block.components.BlockTransactionItemState
import com.etasdemir.ethinspector.utils.toDecimal

fun mapBlockResponseToBlockState(response: EtherscanRPCResponse<BlockResponse>): BlockDetailState {
    val blockResponse = response.result
    val mappedTransactions = ArrayList<BlockTransactionItemState>()
    for (transaction in blockResponse.transactions) {
        mappedTransactions.add(
            BlockTransactionItemState(transaction.hash, transaction.value.toDecimal())
        )
    }

    return BlockDetailState(
        blockNumber = blockResponse.number.toDecimal(),
        timestamp = blockResponse.timestamp.toDecimal().toString(),
        txCount = blockResponse.transactions.size,
        minerAddress = blockResponse.miner,
        gasLimit = blockResponse.gasLimit.toDecimal(),
        gasUsed = blockResponse.gasUsed.toDecimal(),
        baseFeePerGas = blockResponse.baseFeePerGas.toDecimal(),
        transactions = mappedTransactions
    )
}