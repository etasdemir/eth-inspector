package com.etasdemir.ethinspector.data.local.entity

import androidx.room.*

@Entity
data class AccountLocal(
    @PrimaryKey
    val accountAddress: String,

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

data class AccountInfoLocal(
    val balanceWei: String,
    val balanceUsd: Double,
    val transactionCount: Long
)