package com.etasdemir.ethinspector.mappers.local_to_domain

import com.etasdemir.ethinspector.data.domain_model.Block
import com.etasdemir.ethinspector.data.domain_model.BlockTransaction
import com.etasdemir.ethinspector.data.local.entity.BlockAndTransactionsRelationEntity

fun mapBlockEntityToBlock(blockTxEntity: BlockAndTransactionsRelationEntity): Block {
    val blockEntity = blockTxEntity.blockEntity
    val transactions = arrayListOf<BlockTransaction>()
    blockTxEntity.transactions.forEach {
        transactions.add(
            BlockTransaction(it.blockNumber.toULong(), it.address, it.amount.toULong())
        )
    }

    return Block(
        blockEntity.blockNumber.toULong(),
        blockEntity.timestamp,
        blockEntity.txCount,
        blockEntity.minerAddress,
        blockEntity.gasLimit.toULong(),
        blockEntity.gasUsed.toULong(),
        blockEntity.baseFeePerGas.toULong(),
        transactions,
        blockEntity.isFavourite,
    )
}