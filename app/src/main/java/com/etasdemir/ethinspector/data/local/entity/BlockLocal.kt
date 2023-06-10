package com.etasdemir.ethinspector.data.local.entity

import androidx.room.*

@Entity
data class BlockLocal(
    @PrimaryKey val blockNumber: ULong,
    val timestamp: String,
    val txCount: Int,
    val minerAddress: String,
    val gasLimit: ULong,
    val gasUsed: ULong,
    val baseFeePerGas: ULong,
    @Relation(
        parentColumn = "blockNumber",
        entityColumn = "fk_blockNumber"
    )
    val transactions: List<BlockTransactionItemLocal>
)

@Entity
data class BlockTransactionItemLocal(
    @PrimaryKey val fk_blockNumber: ULong,
    val address: String,
    val amount: ULong,
)