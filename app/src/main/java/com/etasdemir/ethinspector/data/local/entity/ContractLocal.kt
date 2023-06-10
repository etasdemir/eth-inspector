package com.etasdemir.ethinspector.data.local.entity

import androidx.room.*

@Entity
data class ContractInfoLocal(
    @PrimaryKey val contractAddress: String,
    val creatorAddress: String,
    val creationTime: String,
    val txCount: Long,
    val balanceWei: String,
    val balanceUsd: Double,

    val isFavourite: Boolean,
    val userGivenName: String,
)

data class ContractAndTransactionsLocal(
    @Embedded
    val contractInfoLocal: ContractInfoLocal,

    @Relation(
        parentColumn = "contractAddress",
        entityColumn = "addressHash"
    )
    val transactions: List<AddressTransactionItemLocal>
)
