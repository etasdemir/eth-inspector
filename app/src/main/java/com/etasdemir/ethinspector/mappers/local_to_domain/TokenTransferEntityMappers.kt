package com.etasdemir.ethinspector.mappers.local_to_domain

import com.etasdemir.ethinspector.data.domain_model.TokenTransfer
import com.etasdemir.ethinspector.data.local.entity.TokenTransferEntity

fun mapTokenTransferEntityToDomain(tokenTransfers: List<TokenTransferEntity>): List<TokenTransfer> {
    val result = arrayListOf<TokenTransfer>()
    tokenTransfers.forEach {
        result.add(
            TokenTransfer(
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