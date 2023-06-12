package com.etasdemir.ethinspector.mappers.domain_to_local

import com.etasdemir.ethinspector.data.domain_model.Block
import com.etasdemir.ethinspector.data.local.entity.*

fun mapBlockToBlockEntity(block: Block): BlockAndTransactionsRelationEntity {
    val blockEntity = BlockEntity(
        block.blockNumber.toLong(),
        block.timestamp,
        block.txCount,
        block.minerAddress,
        block.gasLimit.toLong(),
        block.gasUsed.toLong(),
        block.baseFeePerGas.toLong(),
        block.isFavourite,
    )
    val transactions = arrayListOf<BlockTransactionEntity>()
    block.transactions.forEach{
        transactions.add(
            BlockTransactionEntity(it.blockNumber.toLong(), it.address, it.amount.toLong())
        )
    }

    return BlockAndTransactionsRelationEntity(blockEntity, transactions)
}