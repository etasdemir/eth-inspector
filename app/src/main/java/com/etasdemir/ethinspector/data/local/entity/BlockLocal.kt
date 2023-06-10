package com.etasdemir.ethinspector.data.local.entity

import androidx.room.*

@Entity
data class BlockLocal constructor(
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

@Entity
data class BlockTransactionItemLocal(
    @PrimaryKey(autoGenerate = false)
    val fk_blockNumber: Long,
    val address: String,
    val amount: Long,
)
data class BlockAndTransactionsLocal(
    @Embedded
    val blockLocal: BlockLocal,

    @Relation(
        parentColumn = "blockNumber",
        entityColumn = "fk_blockNumber"
    )
    val transactions: List<BlockTransactionItemLocal>
)

