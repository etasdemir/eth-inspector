package com.etasdemir.ethinspector.data.local.entity

import androidx.room.*

@Entity
data class ContractLocal(
    @PrimaryKey val contractAddress: String,

    @Embedded
    val contractInfoLocal: ContractInfoLocal,

    @Relation(
        parentColumn = "contractAddress",
        entityColumn = "addressHash"
    )
    val transactions: List<AddressTransactionItemLocal>
)

data class ContractInfoLocal(
    val creatorAddress: String,
    val creationTime: String,
    val txCount: Long,
    val balanceWei: String,
    val balanceUsd: Double
)
