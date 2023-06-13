package com.etasdemir.ethinspector.data.local.entity

import androidx.room.*

@Entity(tableName = "account_info")
data class AccountInfoEntity(
    @PrimaryKey
    val accountAddress: String,
    val balanceWei: String,
    val balanceUsd: Double,
    val transactionCount: Long,

    val isFavourite: Boolean,
    val userGivenName: String?,
)

data class AccountRelationEntity(
    @Embedded
    val accountInfo: AccountInfoEntity,

    @Relation(
        parentColumn = "accountAddress",
        entityColumn = "addressHash"
    )
    val transactions: List<AddressTransactionEntity>,

    @Relation(
        parentColumn = "accountAddress",
        entityColumn = "ownerAddress"
    )
    val tokens: List<AddressTokenEntity>,

    @Relation(
        parentColumn = "accountAddress",
        entityColumn = "addressHash"
    )
    var transfers: List<TransferEntity>
)