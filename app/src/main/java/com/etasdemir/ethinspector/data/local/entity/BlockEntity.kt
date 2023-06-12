package com.etasdemir.ethinspector.data.local.entity

import androidx.room.*

@Entity(tableName = "block")
data class BlockEntity constructor(
    @PrimaryKey(autoGenerate = false)
    val blockNumber: Long,
    val timestamp: String,
    val txCount: Int,
    val minerAddress: String,
    val gasLimit: Long,
    val gasUsed: Long,
    val baseFeePerGas: Long,

    val isFavourite: Boolean
)

@Entity("block_transaction")
data class BlockTransactionEntity(
    val blockNumber: Long,
    @PrimaryKey
    val address: String,
    val amount: Long,
)
data class BlockAndTransactionsRelationEntity(
    @Embedded
    val blockEntity: BlockEntity,

    @Relation(
        parentColumn = "blockNumber",
        entityColumn = "blockNumber"
    )
    val transactions: List<BlockTransactionEntity>
)

