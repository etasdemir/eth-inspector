package com.etasdemir.ethinspector.data.local.entity

import androidx.room.*

@Entity(tableName = "contract_info")
data class ContractInfoEntity(
    @PrimaryKey val contractAddress: String,
    val creatorAddress: String,
    val creationTime: String,
    val txCount: Long,
    val balanceWei: String,
    val balanceUsd: Double,

    val isFavourite: Boolean,
    val userGivenName: String,
)

data class ContractAndTransactionsRelationEntity(
    @Embedded
    val contractInfoEntity: ContractInfoEntity,

    @Relation(
        parentColumn = "contractAddress",
        entityColumn = "addressHash"
    )
    val transactions: List<AddressTransactionEntity>
)
