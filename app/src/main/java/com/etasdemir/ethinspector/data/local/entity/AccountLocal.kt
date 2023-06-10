package com.etasdemir.ethinspector.data.local.entity

import androidx.room.*

@Entity
data class AccountInfoLocal(
    @PrimaryKey
    val accountAddress: String,
    val balanceWei: String,
    val balanceUsd: Double,
    val transactionCount: Long,

    val isFavourite: Boolean,
    val userGivenName: String,
)

data class AccountLocal(
    @Embedded
    val accountInfo: AccountInfoLocal,

    @Relation(
        parentColumn = "accountAddress",
        entityColumn = "addressHash"
    )
    val transactions: List<AddressTransactionItemLocal>,

    @Relation(
        parentColumn = "accountAddress",
        entityColumn = "symbol",
        associateBy = Junction(TokenItemAccountCrossRef::class)
    )
    val tokens: List<TokenItemLocal>,

    @Relation(
        parentColumn = "accountAddress",
        entityColumn = "addressHash"
    )
    var transfers: List<TransferItemLocal>
)