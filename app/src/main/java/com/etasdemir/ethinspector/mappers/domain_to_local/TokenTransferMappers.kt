package com.etasdemir.ethinspector.mappers.domain_to_local

import com.etasdemir.ethinspector.data.domain_model.TokenTransfer
import com.etasdemir.ethinspector.data.local.entity.TokenTransferEntity

fun mapTokenTransfersToEntity(tokenTransfers: List<TokenTransfer>): List<TokenTransferEntity> {
    val result = arrayListOf<TokenTransferEntity>()
    tokenTransfers.forEach {
        result.add(
            TokenTransferEntity(
                it.blockNumber,
                it.timeStamp,
                it.hash,
                it.blockHash,
                it.from,
                it.contractAddress,
                it.to,
                it.value,
                it.tokenName,
                it.tokenSymbol,
                it.tokenDecimal,
                it.gas,
                it.gasPrice,
                it.gasUsed,
                it.cumulativeGasUsed,
            )
        )
    }
    return result
}